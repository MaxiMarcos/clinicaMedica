package com.clinica.gestionMedica.service.impl;

import com.clinica.gestionMedica.dto.PrestacionRequestDTO;
import com.clinica.gestionMedica.dto.TurnoDto;
import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.entity.Turno;
import com.clinica.gestionMedica.enums.*;
import com.clinica.gestionMedica.mapper.TurnoMapper;
import com.clinica.gestionMedica.repository.PacienteRepository;
import com.clinica.gestionMedica.repository.TurnoRepository;
import com.clinica.gestionMedica.service.ITurnoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoService implements ITurnoService {

    // CAMBIAR LLAMADOS A REPOR POR SERVICE

    private final TurnoRepository turnoRepo;
    private final TurnoMapper turnoMapper;
    private final PacienteRepository pacienteRepository;
    private final EmailService emailService;

    public TurnoService(TurnoRepository TurnoRepo, TurnoMapper TurnoMapper, PacienteRepository pacienteRepository, EmailService emailService) {
        this.turnoRepo = TurnoRepo;
        this.turnoMapper = TurnoMapper;
        this.pacienteRepository = pacienteRepository;
        this.emailService = emailService;
    }

    //metodo admin
    @Override
    public Turno crearTurno(Turno turno) {

        return turnoRepo.save(turno);
    }

    // metodo paciente
    public TurnoDto agregarPrestacionEnTurno(Long pacienteId, Long turnoId) {

        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado con ID: " + pacienteId));

        Turno turno = turnoRepo.findById(turnoId)
                .orElseThrow(() -> new EntityNotFoundException("Turno no encontrado con ID: " + turnoId));

        this.validarTurnoParaPaciente(turno, paciente);

        turno.setPaciente(paciente);
        turno.setEstado(PresenciaEnum.RESERVADO);
        turnoRepo.save(turno);

        TurnoDto turnoDto = turnoMapper.conversionADto(turno);

        this.emailPersonalizado(paciente, turnoDto);

        return turnoDto;
    }

    @Override
    public Turno editarTurno(Long id, Turno turno) {
        Turno nuevoTurno = this.traerTurno(id);
        nuevoTurno.setPaciente(turno.getPaciente());
        nuevoTurno.setEstado(turno.getEstado());
        nuevoTurno.setPrestacion(turno.getPrestacion());
        nuevoTurno.setFechaConsulta(turno.getFechaConsulta());
        nuevoTurno.setMedico(turno.getMedico());

        return turnoRepo.save(nuevoTurno);
    }

    public void validarTurnoParaPaciente(Turno Turno, Paciente paciente) {
        if (Turno.getEstado() != PresenciaEnum.DISPONIBLE) {
            throw new IllegalArgumentException("Este turno ya no está disponible.");
        }

        PrestacionTiposEnum tipo = Turno.getPrestacion().getTipo();
        ObraSocialEnum obra = paciente.getObraSocial();

        if (obra == ObraSocialEnum.NINGUNA) {
            throw new IllegalArgumentException("Necesita una obra social para Turnor este turno.");
        }

        boolean cubierto =
                (obra == ObraSocialEnum.IOSFA && (tipo == PrestacionTiposEnum.CONSULTA_GENERAL || tipo == PrestacionTiposEnum.ECOGRAFIA))
                        || obra == ObraSocialEnum.OSDE;

        if (!cubierto) {
            throw new IllegalArgumentException("Su obra social no cubre este estudio.");
        }
    }

    public List<Turno> buscarPorEspecialidadDisponibilidad(PrestacionRequestDTO prestacionRequestDTO){

        Paciente paciente = pacienteRepository.findById(prestacionRequestDTO.getPacienteId())
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado con ID: " + prestacionRequestDTO.getPacienteId()));


        boolean cubierto = false;

        if (paciente.getObraSocial() == ObraSocialEnum.IOSFA && (prestacionRequestDTO.getTipo() == PrestacionTiposEnum.CONSULTA_GENERAL ||
                prestacionRequestDTO.getTipo() == PrestacionTiposEnum.ECOGRAFIA)) {
            cubierto = true;
        } else if (paciente.getObraSocial() == ObraSocialEnum.OSDE) {
            cubierto = true;
        } else if (paciente.getObraSocial() == ObraSocialEnum.NINGUNA &&
                prestacionRequestDTO.getTipo() == PrestacionTiposEnum.CONSULTA_GENERAL) {
            cubierto = true;
        }

        if (cubierto) {
            return turnoRepo.findByPrestacion_TipoAndEstado(prestacionRequestDTO.getTipo(), PresenciaEnum.DISPONIBLE);
        } else {
            throw new IllegalArgumentException("Su obra social no cubre el estudio. Comuníquese con administración."); // cambiar a una excepción personalizada
        }
    }


    @Override
    public Turno traerTurno(Long id) {
        return turnoRepo.findById(id).orElse(null);
    }

   // @Override
   // public List<TurnoDto> traerHistorialPaciente(String dni) {

     //   List<Turno> historial = TurnoRepo.findByPaciente_Dni(dni);
     //   return TurnoMapper.conversionAListaDto(historial);
   // }

    @Override
    public List<Turno> traerTurnos() {
        return turnoRepo.findAll();
    }

    @Override
    public void eliminarTurno(Long id) {
        turnoRepo.deleteById(id);
    }

    private void emailPersonalizado(Paciente paciente, TurnoDto TurnoDto){

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
                TurnoDto.getCodigoTurno(),
                TurnoDto.getFechaConsulta().toString(),
                TurnoDto.getPrecioTotal(),
                TurnoDto.getEstado()
        );

// 3. Enviar el correo
        emailService.enviarCorreo(
                paciente.getEmail(),
                "Turno confirmado - Clínica Ejemplo",
                cuerpo
        );
    }
}
