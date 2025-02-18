package com.clinica.gestionMedica.service.impl;

import com.clinica.gestionMedica.entity.Medico;
import com.clinica.gestionMedica.entity.Prestacion;
import com.clinica.gestionMedica.enums.PrestacionEstadoEnum;
import com.clinica.gestionMedica.enums.PrestacionTiposEnum;
import com.clinica.gestionMedica.repository.MedicoRepository;
import com.clinica.gestionMedica.repository.PrestacionRepository;
import com.clinica.gestionMedica.service.IPrestacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PrestacionService implements IPrestacionService {

    @Autowired
    PrestacionRepository prestacionRepo;
    @Autowired
    MedicoService medicoService;


    public Prestacion crearPrestacionAdmin(Prestacion prestacion) {

        return prestacionRepo.save(prestacion);
    }

    @Override
    public Prestacion crearPrestacion(Prestacion prestacion) {


        Prestacion prestacionNueva = this.traerPrestacionPorTipoYFecha(prestacion.getTipo(), prestacion.getFechaConsulta(), prestacion.getMedico());


        List<Medico> medicos = medicoService.buscarPorEspecialidad(prestacionNueva.getTipo());

        Medico medicoSeleccionado = prestacionNueva.getMedico();

        // Validamos si el médico pertenece a la especialización correcta
        boolean coincide = medicos.stream()
                .anyMatch(m -> m.getId().equals(medicoSeleccionado.getId()));

        if (coincide) {
            return prestacionRepo.save(prestacionNueva);
        } else {
            // Si no coincide, asignamos el primer médico disponible de la lista
            System.out.println("El médico elegido no atiende esta especialidad, se asignará uno que si: ");
            prestacion.setMedico(medicos.get(0));
            return prestacionRepo.save(prestacion);
        }
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

    public Prestacion traerPrestacionPorTipoYFecha(PrestacionTiposEnum nombre, LocalDateTime fecha, Medico medico) {
       Prestacion prestacion = prestacionRepo.findByTipoAndFechaConsultaAndEstadoAndMedico(nombre, fecha, PrestacionEstadoEnum.DISPONIBLE, medico);

        if (prestacion == null) {
            throw new RuntimeException(String.format("No se encontró una prestación disponible con tipo: %s, fecha: %s, médico: %s",
                    nombre, fecha, medico.getNombre())); // Suponiendo que Medico tiene un método getNombre()
        }

        return prestacion;
    }

    @Override
    public List<Prestacion> traerPrestaciones() {
        return prestacionRepo.findAll();
    }


    @Override
    public void eliminarPrestacion(Long id) {

        prestacionRepo.deleteById(id);
    }
}
