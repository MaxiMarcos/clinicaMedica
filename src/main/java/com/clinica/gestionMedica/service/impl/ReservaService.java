package com.clinica.gestionMedica.service.impl;

import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.entity.Prestacion;
import com.clinica.gestionMedica.entity.Reserva;
import com.clinica.gestionMedica.repository.PacienteRepository;
import com.clinica.gestionMedica.repository.ReservaRepository;
import com.clinica.gestionMedica.service.IReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservaService implements IReservaService {

    private final ReservaRepository reservaRepo;


    public ReservaService(ReservaRepository reservaRepo) {
        this.reservaRepo = reservaRepo;
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
        nuevaReserva.setMedico(reserva.getMedico());
        nuevaReserva.setPaciente(reserva.getPaciente());
        nuevaReserva.setPrecioTotal(reserva.getPrecioTotal());
        nuevaReserva.setPresencia(reserva.getPresencia());
        nuevaReserva.setPrestaciones(reserva.getPrestaciones());

        return reservaRepo.save(nuevaReserva);
    }

    @Override
    public Reserva traerReserva(Long id) {
        return reservaRepo.findById(id).orElse(null);
    }

    @Override
    public List<Reserva> traerReservas() {
        return reservaRepo.findAll();
    }

    @Override
    public void eliminarReserva(Long id) {
        reservaRepo.deleteById(id);
    }
}
