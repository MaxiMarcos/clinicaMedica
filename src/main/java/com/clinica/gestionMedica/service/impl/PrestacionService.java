package com.clinica.gestionMedica.service.impl;

import com.clinica.gestionMedica.dto.PrestacionRequestDto;
import com.clinica.gestionMedica.dto.PrestacionResponseDto;
import com.clinica.gestionMedica.entity.Medico;
import com.clinica.gestionMedica.entity.Prestacion;
import com.clinica.gestionMedica.excepciones.medico.MedicoNoEncontradoException;
import com.clinica.gestionMedica.excepciones.medico.MedicoYaExisteException;
import com.clinica.gestionMedica.excepciones.prestacion.PrestacionNoEncontradaException;
import com.clinica.gestionMedica.excepciones.prestacion.PrestacionYaExisteException;
import com.clinica.gestionMedica.mapper.PrestacionMapper;
import com.clinica.gestionMedica.repository.PrestacionRepository;
import com.clinica.gestionMedica.service.IPrestacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrestacionService implements IPrestacionService {

    private final PrestacionRepository prestacionRepo;
    private final PrestacionMapper prestacionMapper;

    @Override
    public PrestacionResponseDto crearPrestacion(PrestacionRequestDto prestacionRequest) {

        Prestacion prestacionExiste = prestacionRepo.findByTipo(prestacionRequest.getTipo());
        if(prestacionExiste != null){
            throw new PrestacionYaExisteException("Ya existe una prestición creada con este nombre/tipo");
        }
        Prestacion prestacion = prestacionMapper.conversionRequestAPrestacion(prestacionRequest);
        prestacionRepo.save(prestacion);
        return prestacionMapper.conversionPrestacionAResponse(prestacion);
    }

    @Override
    public PrestacionResponseDto editarPrestacion(Long id, PrestacionRequestDto prestacionRequest) {

        Prestacion prestacion = prestacionRepo.findById(id)
                .orElseThrow(PrestacionNoEncontradaException::new);
        if(prestacionRequest.getTipo() != null) prestacion.setTipo(prestacionRequest.getTipo());
        if(prestacionRequest.getDescripcion() != null) prestacion.setDescripcion(prestacionRequest.getDescripcion());
        if(prestacionRequest.getPrecio() != null) prestacion.setPrecio(prestacionRequest.getPrecio());

        return prestacionMapper.conversionPrestacionAResponse(prestacion);
    }

    @Override
    public PrestacionResponseDto traerPrestacion(Long id) {
        Prestacion prestacion = prestacionRepo.findById(id)
                .orElseThrow(PrestacionNoEncontradaException::new);
        return prestacionMapper.conversionPrestacionAResponse(prestacion);
    }


    @Override
    public List<PrestacionResponseDto> traerPrestaciones() {
        return prestacionMapper.conversionPrestacionesAResponse(prestacionRepo.findAll());
    }


    @Override
    public void eliminarPrestacion(Long id) {
        Prestacion prestacion = prestacionRepo.findById(id)
                .orElseThrow(PrestacionNoEncontradaException::new);
        prestacionRepo.deleteById(prestacion.getId());
    }
}
