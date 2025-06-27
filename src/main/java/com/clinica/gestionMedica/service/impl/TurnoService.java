package com.clinica.gestionMedica.service.impl;

import com.clinica.gestionMedica.dto.TurnoBusquedaRequestDto;
import com.clinica.gestionMedica.dto.TurnoRequestDto;
import com.clinica.gestionMedica.dto.TurnoResponseDto;
import com.clinica.gestionMedica.entity.Medico;
import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.entity.Prestacion;
import com.clinica.gestionMedica.entity.Turno;
import com.clinica.gestionMedica.enums.*;
import com.clinica.gestionMedica.excepciones.medico.MedicoNoEncontradoException;
import com.clinica.gestionMedica.excepciones.paciente.PacienteNoEncontradoException;
import com.clinica.gestionMedica.excepciones.prestacion.PrestacionNoCubiertaException;
import com.clinica.gestionMedica.excepciones.prestacion.PrestacionNoEncontradaException;
import com.clinica.gestionMedica.excepciones.turno.ObraSocialRequeridaException;
import com.clinica.gestionMedica.excepciones.turno.TurnoNoDisponibleException;
import com.clinica.gestionMedica.excepciones.turno.TurnoNoEncontradoException;
import com.clinica.gestionMedica.mapper.MedicoMapper;
import com.clinica.gestionMedica.mapper.TurnoMapper;
import com.clinica.gestionMedica.repository.MedicoRepository;
import com.clinica.gestionMedica.repository.PacienteRepository;
import com.clinica.gestionMedica.repository.PrestacionRepository;
import com.clinica.gestionMedica.repository.TurnoRepository;
import com.clinica.gestionMedica.service.ITurnoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TurnoService implements ITurnoService {

    private final TurnoRepository turnoRepo;
    private final TurnoMapper turnoMapper;
    private final PacienteRepository pacienteRepository;
    private final EmailService emailService;
    private final PrestacionRepository prestacionRepository;
    private final MedicoRepository medicoRepository;

    //metodo admin
    @Override
    public TurnoResponseDto crearTurno(TurnoRequestDto turnoRequest) {

        int ultimoCodigo = turnoRepo.findMaxCodigoTurno().orElse(0);
        turnoRequest.setCodigoTurno(ultimoCodigo + 1);

        Paciente paciente = null;
        if (turnoRequest.getPacienteId() != null){
            paciente = pacienteRepository.findById(turnoRequest.getPacienteId()).orElseThrow(PacienteNoEncontradoException::new);
        }

        Medico medico = medicoRepository.findById(turnoRequest.getMedicoId()).orElseThrow(MedicoNoEncontradoException::new);
        Prestacion prestacion = prestacionRepository.findById(turnoRequest.getPrestacionId()).orElseThrow(PrestacionNoEncontradaException::new);

        Turno turno = turnoMapper.conversionRequestATurno(turnoRequest, medico, prestacion, paciente);
        turnoRepo.save(turno);
        return turnoMapper.conversionTurnoAResponse(turno);
    }

    // metodo paciente
    @Override
    public TurnoResponseDto reservarTurno(Long pacienteId, Long turnoId) {

        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(PacienteNoEncontradoException::new);

        Turno turno = turnoRepo.findById(turnoId)
                .orElseThrow(TurnoNoEncontradoException::new);

        this.validarTurnoParaPaciente(turno, paciente);

        turno.setPaciente(paciente);
        turno.setEstado(PresenciaEnum.RESERVADO);
        TurnoResponseDto turnoResponseDto = turnoMapper.conversionTurnoAResponse(turno);

        this.emailPersonalizado(paciente, turnoResponseDto);

        turnoRepo.save(turno);
        return turnoResponseDto;
    }

    @Override
    public TurnoResponseDto editarTurno(Long id, TurnoRequestDto turnoRequest) {
        Turno turno = turnoRepo.findById(id)
                .orElseThrow(TurnoNoEncontradoException::new);
        if(turnoRequest.getPacienteId() != null) turno.setPaciente(turno.getPaciente());
        if(turnoRequest.getEstado() != null) turno.setEstado(turno.getEstado());
        if(turnoRequest.getPrestacionId() != null) turno.setPrestacion(turno.getPrestacion());
        if(turnoRequest.getFechaConsulta() != null) turno.setFechaConsulta(turno.getFechaConsulta());
        if(turnoRequest.getMedicoId() != null) turno.setMedico(turno.getMedico());
        turnoRepo.save(turno);

        return turnoMapper.conversionTurnoAResponse(turno);

    }

    public void validarTurnoParaPaciente(Turno turno, Paciente paciente) {
        if (turno.getEstado() != PresenciaEnum.DISPONIBLE) {
            throw new TurnoNoDisponibleException();
        }

        TipoPrestacion tipo = turno.getPrestacion().getTipoPrestacion();
        ObraSocialEnum obra = paciente.getObraSocial();

        if (obra == ObraSocialEnum.NINGUNA) {
            throw new ObraSocialRequeridaException();
        }

        boolean cubierto =
                (obra == ObraSocialEnum.IOSFA && (tipo == TipoPrestacion.CONSULTA_GENERAL || tipo == TipoPrestacion.ECOGRAFIA))
                        || obra == ObraSocialEnum.OSDE;

        if (!cubierto) {
            throw new PrestacionNoCubiertaException();
        }
    }

    public List<TurnoResponseDto> buscarPorEspecialidadDisponibilidad(TurnoBusquedaRequestDto requestDto){

        Paciente paciente = pacienteRepository.findById(requestDto.getPacienteId())
                .orElseThrow(PacienteNoEncontradoException::new);


        boolean cubierto = false;

        if (paciente.getObraSocial() == ObraSocialEnum.IOSFA && (requestDto.getTipo() == TipoPrestacion.CONSULTA_GENERAL ||
                requestDto.getTipo() == TipoPrestacion.ECOGRAFIA)) {
            cubierto = true;
        } else if (paciente.getObraSocial() == ObraSocialEnum.OSDE) {
            cubierto = true;
        } else if (paciente.getObraSocial() == ObraSocialEnum.NINGUNA &&
                requestDto.getTipo() == TipoPrestacion.CONSULTA_GENERAL) {
            cubierto = true;
        }

        if (cubierto) {
            List<Turno> listaTurnos = turnoRepo.findByPrestacion_TipoPrestacionAndEstado(requestDto.getTipo(), PresenciaEnum.DISPONIBLE);
            return turnoMapper.conversionTurnosAResponse(listaTurnos);
        } else {
            throw new PrestacionNoCubiertaException();
        }
    }


    @Override
    public TurnoResponseDto traerTurno(Long id) {
        Turno turno = turnoRepo.findById(id)
                .orElseThrow(TurnoNoEncontradoException::new);
        return turnoMapper.conversionTurnoAResponse(turno);
    }

    @Override
    public List<TurnoResponseDto> traerTurnos() {

        return turnoMapper.conversionTurnosAResponse(turnoRepo.findAll());
    }

    @Override
    public TurnoResponseDto cancelarTurno(Long pacienteId, Long turnoId) {
        Turno turno = turnoRepo.findById(turnoId).orElseThrow(TurnoNoEncontradoException::new);
        Paciente paciente = pacienteRepository.findById(pacienteId).orElseThrow(PacienteNoEncontradoException::new);
        if(turno.getEstado() == PresenciaEnum.RESERVADO){
            turno.setEstado(PresenciaEnum.DISPONIBLE);
        } else {
            throw new RuntimeException("No se encontró el turno"); //falta excepcion personalizada
        }
        if(turno.getPaciente() == paciente){
            turno.setPaciente(null);
        }else {
            throw new RuntimeException("No se encontró el turno"); //falta excepcion personalizada
        }

        turnoRepo.save(turno);
        return turnoMapper.conversionTurnoAResponse(turno);
    }

    @Override
    public void eliminarTurno(Long id) {
        Turno turno = turnoRepo.findById(id)
                .orElseThrow(TurnoNoEncontradoException::new);
        turnoRepo.deleteById(turno.getId());
    }

    private void emailPersonalizado(Paciente paciente, TurnoResponseDto turnoResponseDto){

        // 2. Preparar el cuerpo del mail
        String cuerpo = """
Hola:

Tu turno fue registrado con éxito.

✅ Código: %d
📅 Fecha: %s
💰 Precio: %d
📌 Estado: %s

Por favor presentate 10 minutos antes.

¡Gracias por confiar en nosotros!
""".formatted(
                turnoResponseDto.getCodigoTurno(),
                turnoResponseDto.getFechaConsulta().toString(),
                turnoResponseDto.getPrecioTotal(),
                turnoResponseDto.getEstado()
        );

// 3. Enviar el correo
        try {emailService.enviarCorreo(
                paciente.getEmail(),
                "Turno confirmado - Clínica Ejemplo",
                cuerpo
        );
    }  catch (Exception e) { // o un tipo más específico como MessagingException
            throw new RuntimeException("Error al enviar el correo de confirmación: " + e.getMessage());
        }
    }

}
