package com.clinica.gestionMedica.service.impl;

import com.clinica.gestionMedica.dto.PrestacionRequestDto;
import com.clinica.gestionMedica.dto.PrestacionResponseDto;
import com.clinica.gestionMedica.entity.Prestacion;
import com.clinica.gestionMedica.enums.TipoPrestacion;
import com.clinica.gestionMedica.excepciones.prestacion.PrestacionNoEncontradaException;
import com.clinica.gestionMedica.excepciones.prestacion.PrestacionYaExisteException;
import com.clinica.gestionMedica.mapper.PrestacionMapper;
import com.clinica.gestionMedica.repository.PrestacionRepository;
import com.clinica.gestionMedica.service.IPrestacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrestacionService implements IPrestacionService {

    private final PrestacionRepository prestacionRepo;
    private final PrestacionMapper prestacionMapper;

    @Override
    public PrestacionResponseDto crearPrestacion(PrestacionRequestDto prestacionRequest) {

        validarPrestacionExistente(prestacionRequest.getTipoPrestacion());

        Prestacion prestacion = prestacionMapper.toEntity(prestacionRequest);
        prestacionRepo.save(prestacion);
        return prestacionMapper.toResponse(prestacion);
    }

    @Override
    public PrestacionResponseDto editarPrestacion(Long id, PrestacionRequestDto prestacionRequest) {

        Prestacion prestacion = prestacionRepo.findById(id)
                .orElseThrow(PrestacionNoEncontradaException::new);

        actualizarPrestacion(prestacion, prestacionRequest);

        prestacionRepo.save(prestacion);
        return prestacionMapper.toResponse(prestacion);
    }

    @Override
    public PrestacionResponseDto traerPrestacion(Long id) {
        Prestacion prestacion = prestacionRepo.findById(id)
                .orElseThrow(PrestacionNoEncontradaException::new);
        return prestacionMapper.toResponse(prestacion);
    }


    @Override
    public List<PrestacionResponseDto> traerPrestaciones() {
        return prestacionMapper.toResponseList(prestacionRepo.findAll());
    }


    @Override
    public void eliminarPrestacion(Long id) {
        Prestacion prestacion = prestacionRepo.findById(id)
                .orElseThrow(PrestacionNoEncontradaException::new);
        prestacionRepo.delete(prestacion);
    }

    private void validarPrestacionExistente(TipoPrestacion tipoPrestacion){
        if (prestacionRepo.findByTipoPrestacion(tipoPrestacion) != null) {
            throw new PrestacionYaExisteException(tipoPrestacion);
        }
    }

    private void actualizarPrestacion(Prestacion prestacion, PrestacionRequestDto request) {
        if (request.getTipoPrestacion() != null) {
            prestacion.setTipoPrestacion(request.getTipoPrestacion());
        }
        if (request.getDescripcion() != null) {
            prestacion.setDescripcion(request.getDescripcion());
        }
        if (request.getPrecio() != null) {
            prestacion.setPrecio(request.getPrecio());
        }
    }

}
