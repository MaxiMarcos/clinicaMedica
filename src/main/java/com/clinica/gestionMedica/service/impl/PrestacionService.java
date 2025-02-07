package com.clinica.gestionMedica.service.impl;

import com.clinica.gestionMedica.entity.Prestacion;
import com.clinica.gestionMedica.repository.PrestacionRepository;
import com.clinica.gestionMedica.service.IPrestacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrestacionService implements IPrestacionService {

    @Autowired
    PrestacionRepository prestacionRepo;

    @Override
    public Prestacion crearPrestacion(Prestacion prestacion) {

        return prestacionRepo.save(prestacion);
    }

    @Override
    public Prestacion editarPrestacion(Long id, Prestacion prestacion) {

        Prestacion nuevaPrestacion = this.traerPrestacion(id);

        nuevaPrestacion.setTipo(prestacion.getTipo());
        nuevaPrestacion.setCodigoPrestacion(prestacion.getCodigoPrestacion());
        nuevaPrestacion.setDescripcion(prestacion.getDescripcion());
        nuevaPrestacion.setPrecio(prestacion.getPrecio());

        return nuevaPrestacion;
    }

    @Override
    public Prestacion traerPrestacion(Long id) {
        return prestacionRepo.findById(id).orElse(null);
    }

    @Override
    public List<Prestacion> traerPrestaciones() {
        return List.of();
    }

    @Override
    public void eliminarPrestacion(Long id) {

    }
}
