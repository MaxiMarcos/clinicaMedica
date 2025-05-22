package com.clinica.gestionMedica.service.impl;

import com.clinica.gestionMedica.dto.PrestacionDto;
import com.clinica.gestionMedica.dto.PrestacionRequestDTO;
import com.clinica.gestionMedica.entity.Medico;
import com.clinica.gestionMedica.entity.Prestacion;
import com.clinica.gestionMedica.enums.MedicoEstadoEnum;
import com.clinica.gestionMedica.enums.PrestacionEstadoEnum;
import com.clinica.gestionMedica.enums.PrestacionTiposEnum;
import com.clinica.gestionMedica.mapper.PrestacionMapper;
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
    private final PrestacionMapper prestacionMapper;

    public PrestacionService(PrestacionRepository prestacionRepo, MedicoService medicoService, PrestacionMapper prestacionMapper) {
        this.prestacionRepo = prestacionRepo;
        this.medicoService = medicoService;
        this.prestacionMapper = prestacionMapper;
    }


    public Prestacion crearPrestacionAdmin(Prestacion prestacion) {

        return prestacionRepo.save(prestacion);
    }

    @Override
    public Prestacion crearPrestacion(Prestacion prestacion) {

        Medico medicoSeleccionado = prestacion.getMedico();

        if (medicoSeleccionado == null) {
            throw new IllegalArgumentException("El médico seleccionado no existe");
        }

        if (!medicoSeleccionado.getEspecializacion().equals(prestacion.getTipo())) {
            throw new IllegalArgumentException("El médico no pertenece a la especialidad requerida");
        }

        if (medicoSeleccionado.getDisponibilidad() != MedicoEstadoEnum.DISPONIBLE) {
            throw new IllegalArgumentException("El médico seleccionado no está disponible actualmente");
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

    @Override
    public PrestacionDto traerPrestacionDto(Long id) {
        Prestacion prestacion = prestacionRepo.findById(id).orElse(null);

        if (prestacion != null) {
            return prestacionMapper.conversionAPrestacionDto(prestacion);
        } else {
            return null;
        }
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
