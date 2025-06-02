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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservaService implements IReservaService {

    // CAMBIAR LLAMADOS A REPOR POR SERVICE

    private final ReservaRepository reservaRepo;
    private final ReservaMapper reservaMapper;
    private final PacienteRepository pacienteRepository;

    public ReservaService(ReservaRepository reservaRepo, ReservaMapper reservaMapper, PacienteRepository pacienteRepository) {
        this.reservaRepo = reservaRepo;
        this.reservaMapper = reservaMapper;
        this.pacienteRepository = pacienteRepository;
    }

    //metodo admin
    @Override
    public Reserva crearReserva(Reserva reserva) {

        return reservaRepo.save(reserva);
    }

    // metodo paciente
    public ReservaDto agregarPrestacionEnReserva(Long pacienteId, Long reservaId) {

        Paciente paciente = pacienteRepository.findById(pacienteId).orElse(null);

        if(paciente == null){
            throw new IllegalArgumentException("El paciente no existe");
        }

        Reserva reserva = reservaRepo.findById(reservaId).orElse(null);
        if (reserva != null) {
            throw new IllegalArgumentException("No se encuentra el turno seleccionado");
        }

        reserva.setPaciente(paciente);
        reserva.setEstado(PresenciaEnum.RESERVADO);


        if(paciente.getObraSocial() != ObraSocialEnum.NINGUNA){
           reserva.setPrecioTotal(0);
        } else{
            reserva.setPrecioTotal(reserva.getPrestacion().getPrecio());
        }

        reservaRepo.save(reserva);

        ReservaDto reservaDto = reservaMapper.conversionADto(reserva);

        return reservaDto;

        // agregar tipos de descuentos
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
}
