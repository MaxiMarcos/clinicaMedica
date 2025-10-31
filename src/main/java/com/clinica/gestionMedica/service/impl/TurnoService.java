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
import com.clinica.gestionMedica.excepciones.medico.MedicosNoEncontradosException;
import com.clinica.gestionMedica.excepciones.paciente.PacienteNoEncontradoException;
import com.clinica.gestionMedica.excepciones.prestacion.PrestacionNoCubiertaException;
import com.clinica.gestionMedica.excepciones.prestacion.PrestacionNoEncontradaException;
import com.clinica.gestionMedica.excepciones.turno.ObraSocialRequeridaException;
import com.clinica.gestionMedica.excepciones.turno.TurnoNoDisponibleException;
import com.clinica.gestionMedica.excepciones.turno.TurnoNoEncontradoException;
import com.clinica.gestionMedica.excepciones.turno.TurnosNoEncontradosException;
import com.clinica.gestionMedica.mapper.TurnoMapper;
import com.clinica.gestionMedica.repository.MedicoRepository;
import com.clinica.gestionMedica.repository.PacienteRepository;
import com.clinica.gestionMedica.repository.PrestacionRepository;
import com.clinica.gestionMedica.repository.TurnoRepository;
import com.clinica.gestionMedica.service.ITurnoService;
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

        turnoRequest.setCodigoTurno(generarCodigoTurno());

        Paciente paciente;
        if (turnoRequest.getPacienteId() != null) {
            paciente = buscarPaciente(turnoRequest.getPacienteId());
        } else {
            paciente = null;
        }
        Medico medico = buscarMedico(turnoRequest.getMedicoId());
        Prestacion prestacion = buscarPrestacion(turnoRequest.getPrestacionId());

        Turno turno = turnoMapper.toEntity(turnoRequest, medico, prestacion, paciente);
        turnoRepo.save(turno);
        return turnoMapper.toResponse(turno);
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
        TurnoResponseDto turnoResponseDto = turnoMapper.toResponse(turno);

        turnoRepo.save(turno);
        this.emailPersonalizado(paciente, turnoResponseDto);

        return turnoResponseDto;
    }

    @Override
    public TurnoResponseDto editarTurno(Long id, TurnoRequestDto turnoRequest) {
        Turno turno = turnoRepo.findById(id)
                .orElseThrow(TurnoNoEncontradoException::new);

        actualizarTurno(turnoRequest, turno);

        turnoRepo.save(turno);
        return turnoMapper.toResponse(turno);

    }


    public List<TurnoResponseDto> buscarPorMedico(TurnoBusquedaRequestDto requestDto){

        Paciente paciente = pacienteRepository.findById(requestDto.getPacienteId())
                .orElseThrow(PacienteNoEncontradoException::new);

        List<Turno> turnos = turnoRepo.findByMedicoId(requestDto.getMedicoId());

        return turnoMapper.toResponseList(turnos);

/** ESTA LÓGICA DEBE IR EN EL SERVICIO QUE ELGIE UNA PRESTACIÓN Y TRAE MEDICOS:
 *
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

        **/

    }

    @Override
    public TurnoResponseDto traerTurno(Long id) {
        Turno turno = turnoRepo.findById(id)
                .orElseThrow(TurnoNoEncontradoException::new);
        return turnoMapper.toResponse(turno);
    }

    @Override
    public List<TurnoResponseDto> traerTurnos() {

        return turnoMapper.toResponseList(turnoRepo.findAll());
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
        return turnoMapper.toResponse(turno);
    }

    @Override
    public void eliminarTurno(Long id) {
        Turno turno = turnoRepo.findById(id)
                .orElseThrow(TurnoNoEncontradoException::new);
        turnoRepo.deleteById(turno.getId());
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

    private void actualizarTurno(TurnoRequestDto turnoRequest, Turno turno){
        if(turnoRequest.getPacienteId() != null) turno.setPaciente(turno.getPaciente());
        if(turnoRequest.getEstado() != null) turno.setEstado(turno.getEstado());
        if(turnoRequest.getPrestacionId() != null) turno.setPrestacion(turno.getPrestacion());
        if(turnoRequest.getFechaConsulta() != null) turno.setFechaConsulta(turno.getFechaConsulta());
        if(turnoRequest.getMedicoId() != null) turno.setMedico(turno.getMedico());
    }

    public int generarCodigoTurno(){

        return turnoRepo.findMaxCodigoTurno().orElse(0) + 1;
    }

    public Paciente buscarPaciente(Long pacienteId){

        return pacienteRepository.findById(pacienteId).orElseThrow(PacienteNoEncontradoException::new);
    }

    private Medico buscarMedico(Long medicoId) {
        return medicoRepository.findById(medicoId).orElseThrow(MedicoNoEncontradoException::new);
    }

    private Prestacion buscarPrestacion(Long prestacionId) {
        return prestacionRepository.findById(prestacionId).orElseThrow(PrestacionNoEncontradaException::new);
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
