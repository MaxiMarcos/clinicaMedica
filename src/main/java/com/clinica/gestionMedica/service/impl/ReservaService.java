package com.clinica.gestionMedica.service.impl;

import com.clinica.gestionMedica.dto.PacienteDto;
import com.clinica.gestionMedica.dto.PrestacionDto;
import com.clinica.gestionMedica.dto.PrestacionRequestDTO;
import com.clinica.gestionMedica.dto.ReservaDto;
import com.clinica.gestionMedica.entity.Medico;
import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.entity.Prestacion;
import com.clinica.gestionMedica.entity.Reserva;
import com.clinica.gestionMedica.enums.*;
import com.clinica.gestionMedica.mapper.PacienteMapper;
import com.clinica.gestionMedica.mapper.ReservaMapper;
import com.clinica.gestionMedica.repository.PacienteRepository;
import com.clinica.gestionMedica.repository.PrestacionRepository;
import com.clinica.gestionMedica.repository.ReservaRepository;
import com.clinica.gestionMedica.service.IReservaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservaService implements IReservaService {

    // CAMBIAR LLAMADOS A REPOR POR SERVICE

    private final ReservaRepository reservaRepo;
    private final ReservaMapper reservaMapper;
    private final PacienteRepository pacienteRepository;
    private final EmailService emailService;

    public ReservaService(ReservaRepository reservaRepo, ReservaMapper reservaMapper, PacienteRepository pacienteRepository, EmailService emailService) {
        this.reservaRepo = reservaRepo;
        this.reservaMapper = reservaMapper;
        this.pacienteRepository = pacienteRepository;
        this.emailService = emailService;
    }

    //metodo admin
    @Override
    public Reserva crearReserva(Reserva reserva) {

        return reservaRepo.save(reserva);
    }

    // metodo paciente
    public ReservaDto agregarPrestacionEnReserva(Long pacienteId, Long reservaId) {

        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado con ID: " + pacienteId));

        Reserva reserva = reservaRepo.findById(reservaId)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada con ID: " + reservaId));

        this.validarTurnoParaPaciente(reserva, paciente);

        reserva.setPaciente(paciente);
        reserva.setEstado(PresenciaEnum.RESERVADO);
        reservaRepo.save(reserva);

        ReservaDto reservaDto = reservaMapper.conversionADto(reserva);

        this.emailPersonalizado(paciente, reservaDto);

        return reservaDto;
    }

    @Override
    public Reserva editarReserva(Long id, Reserva reserva) {
        Reserva nuevaReserva = this.traerReserva(id);
        nuevaReserva.setPaciente(reserva.getPaciente());
        nuevaReserva.setPrecioTotal(reserva.getPrecioTotal());
        nuevaReserva.setEstado(reserva.getEstado());
        nuevaReserva.setPrestacion(reserva.getPrestacion());
        nuevaReserva.setFechaConsulta(reserva.getFechaConsulta());
        nuevaReserva.setMedico(reserva.getMedico());

        return reservaRepo.save(nuevaReserva);
    }

    public void validarTurnoParaPaciente(Reserva reserva, Paciente paciente) {
        if (reserva.getEstado() != PresenciaEnum.DISPONIBLE) {
            throw new IllegalArgumentException("Este turno ya no está disponible.");
        }

        PrestacionTiposEnum tipo = reserva.getPrestacion().getTipo();
        ObraSocialEnum obra = paciente.getObraSocial();

        if (obra == ObraSocialEnum.NINGUNA) {
            throw new IllegalArgumentException("Necesita una obra social para reservar este turno.");
        }

        boolean cubierto =
                (obra == ObraSocialEnum.IOSFA && (tipo == PrestacionTiposEnum.CONSULTA_GENERAL || tipo == PrestacionTiposEnum.ECOGRAFIA))
                        || obra == ObraSocialEnum.OSDE;

        if (!cubierto) {
            throw new IllegalArgumentException("Su obra social no cubre este estudio.");
        }
    }

    public List<Reserva> buscarPorEspecialidadDisponibilidad(PrestacionRequestDTO prestacionRequestDTO){

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
            return reservaRepo.findByPrestacion_TipoAndEstado(prestacionRequestDTO.getTipo(), PresenciaEnum.DISPONIBLE);
        } else {
            throw new IllegalArgumentException("Su obra social no cubre el estudio. Comuníquese con administración."); // cambiar a una excepción personalizada
        }
    }


    @Override
    public Reserva traerReserva(Long id) {
        return reservaRepo.findById(id).orElse(null);
    }

   // @Override
   // public List<ReservaDto> traerHistorialPaciente(String dni) {

     //   List<Reserva> historial = reservaRepo.findByPaciente_Dni(dni);
     //   return reservaMapper.conversionAListaDto(historial);
   // }

    @Override
    public List<Reserva> traerReservas() {
        return reservaRepo.findAll();
    }

    @Override
    public void eliminarReserva(Long id) {
        reservaRepo.deleteById(id);
    }

    private void emailPersonalizado(Paciente paciente, ReservaDto reservaDto){

        // 2. Preparar el cuerpo del mail
        String cuerpo = """
Hola:

Tu turno fue reservado con éxito.

✅ Código: %d
📅 Fecha: %s
💰 Precio: %d
📌 Estado: %s

Por favor presentate 10 minutos antes.

¡Gracias por confiar en nosotros!
""".formatted(
                reservaDto.getCodigoTurno(),
                reservaDto.getFechaConsulta().toString(),
                reservaDto.getPrecioTotal(),
                reservaDto.getEstado()
        );

// 3. Enviar el correo
        emailService.enviarCorreo(
                paciente.getEmail(),
                "Turno confirmado - Clínica Ejemplo",
                cuerpo
        );
    }
}
