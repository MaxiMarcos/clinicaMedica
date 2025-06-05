package com.clinica.gestionMedica.service;

import com.clinica.gestionMedica.dto.PrestacionRequestDto;
import com.clinica.gestionMedica.dto.PrestacionResponseDto;
import com.clinica.gestionMedica.entity.Prestacion;

import java.util.List;

public interface IPrestacionService {

    PrestacionResponseDto crearPrestacion(PrestacionRequestDto prestacionRequest);
    PrestacionResponseDto editarPrestacion(Long id, PrestacionRequestDto prestacionRequest);
    PrestacionResponseDto traerPrestacion(Long id);
    List<PrestacionResponseDto> traerPrestaciones();
    void eliminarPrestacion(Long id);
}
