package com.clinica.gestionMedica.service.impl;

import com.clinica.gestionMedica.entity.Reserva;
import com.clinica.gestionMedica.mapper.ReservaMapper;
import com.clinica.gestionMedica.repository.ReservaRepository;
import com.clinica.gestionMedica.service.IReservaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService implements IReservaService {

    private final ReservaRepository reservaRepo;
    private final ReservaMapper reservaMapper;

    public ReservaService(ReservaRepository reservaRepo, PacienteService pacienteService, ReservaMapper reservaMapper) {
        this.reservaRepo = reservaRepo;
        this.reservaMapper = reservaMapper;
    }

    @Override
    public Reserva crearReserva(Reserva reserva) {

        //Paciente paciente = pacienteRepo.findByDni(reserva.getPaciente().getDni());

        return reservaRepo.save(reserva);
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
