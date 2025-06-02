package com.clinica.gestionMedica.service.impl;

import com.clinica.gestionMedica.dto.PrestacionDto;
import com.clinica.gestionMedica.dto.PrestacionRequestDTO;
import com.clinica.gestionMedica.entity.Prestacion;
import com.clinica.gestionMedica.enums.PrestacionEstadoEnum;
import com.clinica.gestionMedica.mapper.PrestacionMapper;
import com.clinica.gestionMedica.repository.PrestacionRepository;
import com.clinica.gestionMedica.service.IPrestacionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrestacionService implements IPrestacionService {

    private final PrestacionRepository prestacionRepo;
    private final PrestacionMapper prestacionMapper;

    public PrestacionService(PrestacionRepository prestacionRepo, MedicoService medicoService, PrestacionMapper prestacionMapper) {
        this.prestacionRepo = prestacionRepo;
        this.prestacionMapper = prestacionMapper;
    }


    @Override
    public Prestacion crearPrestacion(Prestacion prestacion) {

        return prestacionRepo.save(prestacion);
    }

    @Override
    public Prestacion editarPrestacion(Long id, Prestacion prestacion) {

        Prestacion nuevaPrestacion = this.traerPrestacion(id);

        nuevaPrestacion.setTipo(prestacion.getTipo());
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

   // public List<Prestacion> buscarPorEspecialidadDisponibilidad(PrestacionRequestDTO prestacionRequestDTO){

  //      return prestacionRepo.findByTipoAndEstado(prestacionRequestDTO.getTipo(), PrestacionEstadoEnum.DISPONIBLE);
  //  }


    @Override
    public List<Prestacion> traerPrestaciones() {
        return prestacionRepo.findAll();
    }


    @Override
    public void eliminarPrestacion(Long id) {

        prestacionRepo.deleteById(id);
    }
}
