package com.clinica.gestionMedica.service.impl;

import com.clinica.gestionMedica.entity.Prestacion;
import com.clinica.gestionMedica.entity.Reserva;
import com.clinica.gestionMedica.repository.ReservaRepository;
import com.clinica.gestionMedica.service.IReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservaService implements IReservaService {

    @Autowired
    ReservaRepository reservaRepo;

    @Override
    public Reserva crearReserva(Reserva reserva) {

        return reservaRepo.save(reserva);
    }

    @Override
    public Reserva editarReserva(Long id, Reserva reserva) {
        Reserva nuevaReserva = this.traerReserva(id);
        nuevaReserva.setEstadoPago(reserva.getEstadoPago());
        nuevaReserva.setMedico(reserva.getMedico());
        nuevaReserva.setPaciente(reserva.getPaciente());
        nuevaReserva.setPrecioTotal(reserva.getPrecioTotal());
        nuevaReserva.setEstadoPresencia(reserva.getEstadoPresencia());

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
