package com.clinica.gestionMedica.service.impl;

import com.clinica.gestionMedica.entity.Medico;
import com.clinica.gestionMedica.entity.Prestacion;
import com.clinica.gestionMedica.repository.MedicoRepository;
import com.clinica.gestionMedica.repository.PrestacionRepository;
import com.clinica.gestionMedica.service.IPrestacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrestacionService implements IPrestacionService {

    @Autowired
    PrestacionRepository prestacionRepo;
    @Autowired
    MedicoService medicoService;

    @Override
    public Prestacion crearPrestacion(Prestacion prestacion) {

        // recibo los médicos filtrando de acuerdo al servicio que necesito
        List<Medico> medicos = medicoService.buscarPorEspecialidad(prestacion.getTipo());
        if (medicos.isEmpty()) {
            throw new RuntimeException("No hay médicos disponibles para la especialización: " + prestacion.getTipo());
        }
        for (Medico medico : medicos) {
            System.out.println("Medicos filtrados por especializacion: " + medico.getApellido());
        }

        Medico medicoSeleccionado = prestacion.getMedico();

        // Validamos si el médico pertenece a la especialización correcta
        boolean coincide = medicos.stream()
                .anyMatch(m -> m.getId().equals(medicoSeleccionado.getId()));

        if (coincide) {
            return prestacionRepo.save(prestacion);
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

    @Override
    public List<Prestacion> traerPrestaciones() {
        return List.of();
    }

    @Override
    public void eliminarPrestacion(Long id) {

    }
}
