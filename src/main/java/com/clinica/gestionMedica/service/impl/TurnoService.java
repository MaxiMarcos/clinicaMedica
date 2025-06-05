package com.clinica.gestionMedica.service.impl;

import com.clinica.gestionMedica.dto.TurnoBusquedaRequestDto;
import com.clinica.gestionMedica.dto.TurnoRequestDto;
import com.clinica.gestionMedica.dto.TurnoResponseDto;
import com.clinica.gestionMedica.entity.Medico;
import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.entity.Turno;
import com.clinica.gestionMedica.enums.*;
import com.clinica.gestionMedica.excepciones.medico.MedicoNoEncontradoException;
import com.clinica.gestionMedica.excepciones.turno.TurnoNoEncontradoException;
import com.clinica.gestionMedica.mapper.TurnoMapper;
import com.clinica.gestionMedica.repository.PacienteRepository;
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

    //metodo admin
    @Override
    public TurnoResponseDto crearTurno(TurnoRequestDto turnoRequest) {

        Turno turno = turnoMapper.conversionRequestATurno(turnoRequest);
        turnoRepo.save(turno);
        return turnoMapper.conversionTurnoAResponse(turno);
    }

    // metodo paciente
    public TurnoResponseDto reservarTurno(Long pacienteId, Long turnoId) {

        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado con ID: " + pacienteId));

        Turno turno = turnoRepo.findById(turnoId)
                .orElseThrow(() -> new EntityNotFoundException("Turno no encontrado con ID: " + turnoId));

        this.validarTurnoParaPaciente(turno, paciente);

        turno.setPaciente(paciente);
        turno.setEstado(PresenciaEnum.RESERVADO);
        turnoRepo.save(turno);

        TurnoResponseDto turnoResponseDto = turnoMapper.conversionTurnoAResponse(turno);

        this.emailPersonalizado(paciente, turnoResponseDto);

        return turnoResponseDto;
    }

    @Override
    public TurnoResponseDto editarTurno(Long id, TurnoRequestDto turnoRequest) {
        Turno turno = turnoRepo.findById(id)
                .orElseThrow(() -> new TurnoNoEncontradoException("Turno no encontrado con id: " + id));
        if(turnoRequest.getPaciente() != null) turno.setPaciente(turno.getPaciente());
        if(turnoRequest.getEstado() != null) turno.setEstado(turno.getEstado());
        if(turnoRequest.getPrestacion() != null) turno.setPrestacion(turno.getPrestacion());
        if(turnoRequest.getFechaConsulta() != null) turno.setFechaConsulta(turno.getFechaConsulta());
        if(turnoRequest.getMedico() != null) turno.setMedico(turno.getMedico());
        turnoRepo.save(turno);

        return turnoMapper.conversionTurnoAResponse(turno);

    }

    public void validarTurnoParaPaciente(Turno Turno, Paciente paciente) {
        if (Turno.getEstado() != PresenciaEnum.DISPONIBLE) {
            throw new IllegalArgumentException("Este turno ya no está disponible.");
        }

        PrestacionTiposEnum tipo = Turno.getPrestacion().getTipo();
        ObraSocialEnum obra = paciente.getObraSocial();

        if (obra == ObraSocialEnum.NINGUNA) {
            throw new IllegalArgumentException("Necesita una obra social para Reservar este turno.");
        }

        boolean cubierto =
                (obra == ObraSocialEnum.IOSFA && (tipo == PrestacionTiposEnum.CONSULTA_GENERAL || tipo == PrestacionTiposEnum.ECOGRAFIA))
                        || obra == ObraSocialEnum.OSDE;

        if (!cubierto) {
            throw new IllegalArgumentException("Su obra social no cubre este estudio.");
        }
    }

    public List<Turno> buscarPorEspecialidadDisponibilidad(TurnoBusquedaRequestDto requestDto){

        Paciente paciente = pacienteRepository.findById(requestDto.getPacienteId())
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado con ID: " + requestDto.getPacienteId()));


        boolean cubierto = false;

        if (paciente.getObraSocial() == ObraSocialEnum.IOSFA && (requestDto.getTipo() == PrestacionTiposEnum.CONSULTA_GENERAL ||
                requestDto.getTipo() == PrestacionTiposEnum.ECOGRAFIA)) {
            cubierto = true;
        } else if (paciente.getObraSocial() == ObraSocialEnum.OSDE) {
            cubierto = true;
        } else if (paciente.getObraSocial() == ObraSocialEnum.NINGUNA &&
                requestDto.getTipo() == PrestacionTiposEnum.CONSULTA_GENERAL) {
            cubierto = true;
        }

        if (cubierto) {
            return turnoRepo.findByPrestacion_TipoAndEstado(requestDto.getTipo(), PresenciaEnum.DISPONIBLE);
        } else {
            throw new IllegalArgumentException("Su obra social no cubre el estudio. Comuníquese con administración."); // cambiar a una excepción personalizada
        }
    }


    @Override
    public TurnoResponseDto traerTurno(Long id) {
        Turno turno = turnoRepo.findById(id)
                .orElseThrow(() -> new TurnoNoEncontradoException("Turno no encontrado con id: " + id));
        return turnoMapper.conversionTurnoAResponse(turno);
    }

    @Override
    public List<TurnoResponseDto> traerTurnos() {

        return turnoMapper.conversionTurnosAResponse(turnoRepo.findAll());
    }

    @Override
    public void eliminarTurno(Long id) {
        Turno turno = turnoRepo.findById(id)
                .orElseThrow(() -> new TurnoNoEncontradoException("Turno no encontrado con id: " + id));
        turnoRepo.deleteById(turno.getId());
    }

    private void emailPersonalizado(Paciente paciente, TurnoResponseDto turnoResponseDto){

        // 2. Preparar el cuerpo del mail
        String cuerpo = """
Hola:

Tu turno fue Turnodo con éxito.

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
        emailService.enviarCorreo(
                paciente.getEmail(),
                "Turno confirmado - Clínica Ejemplo",
                cuerpo
        );
    }
}
