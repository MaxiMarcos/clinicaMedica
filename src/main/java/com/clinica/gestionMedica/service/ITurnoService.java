package com.clinica.gestionMedica.service;


import com.clinica.gestionMedica.entity.Turno;

import java.util.List;

public interface ITurnoService{

    Turno crearTurno(Turno turno);
    Turno editarTurno(Long id, Turno turno);
    Turno traerTurno(Long id);
    List<Turno> traerTurnos();
   // List<TurnoDto> traerHistorialPaciente(String dni);
    void eliminarTurno(Long id);

}
