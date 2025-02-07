package com.clinica.gestionMedica.service;

import com.clinica.gestionMedica.entity.Prestacion;

import java.util.List;

public interface IPrestacionService {

    Prestacion crearPrestacion(Prestacion prestacion);
    Prestacion editarPrestacion(Long id, Prestacion prestacion);
    Prestacion traerPrestacion(Long id);
    List<Prestacion> traerPrestaciones();
    void eliminarPrestacion(Long id);
}
