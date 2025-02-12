package com.clinica.gestionMedica.service;


import com.clinica.gestionMedica.entity.Prestacion;
import com.clinica.gestionMedica.entity.Reserva;

import java.util.List;

public interface IReservaService{

    Reserva crearReserva(Reserva reserva);
    Reserva editarReserva(Long id, Reserva reserva);
    Reserva traerReserva(Long id);
    List<Reserva> traerReservas();
    void eliminarReserva(Long id);

}
