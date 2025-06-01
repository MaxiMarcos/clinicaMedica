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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservaService implements IReservaService {

    // CAMBIAR LLAMADOS A REPOR POR SERVICE

    private final ReservaRepository reservaRepo;
    private final PrestacionService prestacionService;
    private final PrestacionRepository prestacionRepo;
    private final ReservaMapper reservaMapper;
    private final PacienteRepository pacienteRepository;

    public ReservaService(ReservaRepository reservaRepo, PacienteService pacienteService, PrestacionService prestacionService, PrestacionRepository prestacionRepo, ReservaMapper reservaMapper, PacienteMapper pacienteMapper, PacienteRepository pacienteRepository) {
        this.reservaRepo = reservaRepo;
        this.prestacionService = prestacionService;
        this.prestacionRepo = prestacionRepo;
        this.reservaMapper = reservaMapper;
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public Reserva crearReserva(Reserva reserva) {

        //Paciente paciente = pacienteRepo.findByDni(reserva.getPaciente().getDni());

        return reservaRepo.save(reserva);
    }

    // puedo cambiar a List<Long> para traer todas las q ameriten
    public ReservaDto agregarPrestacionEnReserva(PrestacionRequestDTO prestacionRequestDTO) {

        Paciente paciente = pacienteRepository.findById(prestacionRequestDTO.getPacienteId()).orElse(null);

        Reserva reserva = new Reserva();
        if(paciente == null){
            throw new IllegalArgumentException("El paciente no existe");
        }

        List<Prestacion> prestacionesDisponibles = prestacionService.buscarPorEspecialidadDisponibilidad(prestacionRequestDTO);
        if (prestacionesDisponibles.isEmpty()) {
            throw new IllegalArgumentException("No hay prestaciones disponibles para reservar");
        }

        for (Prestacion p : prestacionesDisponibles) {
            Medico m = p.getMedico();
            if (m.getDisponibilidad() != MedicoEstadoEnum.DISPONIBLE) {
                throw new IllegalArgumentException("El médico " + m.getNombre() + " ya no está disponible para la prestación");
            }
        }

        reserva.setPaciente(paciente);
        reserva.setEstadoPago(ReservaEstadoEnum.PENDIENTE);

        // se mantiene el precio de cada prestacion solo si el paciente no tiene obra social
        if(paciente.getObraSocial() != ObraSocialEnum.NINGUNA){
            for(Prestacion p : prestacionesDisponibles){
                p.setPrecio(0);
            }
        }
        for (Prestacion p : prestacionesDisponibles) {
            p.setEstado(PrestacionEstadoEnum.NODISPONIBLE);
            p.setReserva(reserva);
        }

        reserva.setPrestaciones(prestacionesDisponibles);
        reservaRepo.save(reserva);

        prestacionRepo.saveAll(prestacionesDisponibles);
        ReservaDto reservaDto = reservaMapper.conversionADto(reserva);

        return reservaDto;

        // cambiar precio total reserva
        // agregar tipos de descuentos
    }

    @Override
    public Reserva editarReserva(Long id, Reserva reserva) {
        Reserva nuevaReserva = this.traerReserva(id);
        nuevaReserva.setEstadoPago(reserva.getEstadoPago());
        nuevaReserva.setPaciente(reserva.getPaciente());
        nuevaReserva.setPrecioTotal(reserva.getPrecioTotal());
        nuevaReserva.setEstadoPresencia(reserva.getEstadoPresencia());
        nuevaReserva.setPrestaciones(reserva.getPrestaciones());

        return reservaRepo.save(nuevaReserva);
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
