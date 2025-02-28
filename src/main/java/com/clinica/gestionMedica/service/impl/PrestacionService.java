package com.clinica.gestionMedica.service.impl;

import com.clinica.gestionMedica.dto.PrestacionRequestDTO;
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

    private final PrestacionRepository prestacionRepo;
    private final MedicoService medicoService;

    public PrestacionService(PrestacionRepository prestacionRepo, MedicoService medicoService) {
        this.prestacionRepo = prestacionRepo;
        this.medicoService = medicoService;
    }


    public Prestacion crearPrestacionAdmin(Prestacion prestacion) {

        return prestacionRepo.save(prestacion);
    }

    @Override
    public Prestacion crearPrestacion(Prestacion prestacion) {

        List<Medico> medicos = medicoService.buscarPorEspecialidad(prestacion.getTipo());
        if(medicos.isEmpty()){
            throw new IllegalArgumentException("Actualmente no trabajamos con esta especialidad");
        }

        Medico medicoSeleccionado = prestacion.getMedico();
        if(medicoSeleccionado == null){
            throw new IllegalArgumentException("El médico seleccionado no existe");
        }

        // Validamos si el médico pertenece a la especialización correcta
        boolean coincide = medicos.stream()
                .anyMatch(m -> m.getId().equals(medicoSeleccionado.getId()));

        if (!coincide) {
            throw new IllegalArgumentException("El médico elegido no atiende esta especialidad");
        }
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

    public List<Prestacion> buscarPorEspecialidadDisponibilidad(PrestacionRequestDTO prestacionRequestDTO){

        return prestacionRepo.findByTipoAndIdInAndEstado(prestacionRequestDTO.getTipo(), prestacionRequestDTO.getPrestacionId(), PrestacionEstadoEnum.DISPONIBLE);
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
