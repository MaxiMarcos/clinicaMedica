package com.clinica.gestionMedica.service;

import com.clinica.gestionMedica.dto.ClinicaRequestDto;
import com.clinica.gestionMedica.dto.ClinicaResponseDto;

import java.util.List;

public interface IClinicaService {
    ClinicaResponseDto crearClinica(ClinicaRequestDto clinicaRequestDto);
    ClinicaResponseDto obtenerClinicaPorId(Long id);
    List<ClinicaResponseDto> obtenerTodasLasClinicas();
    ClinicaResponseDto actualizarClinica(Long id, ClinicaRequestDto clinicaRequestDto);
    void eliminarClinica(Long id);
    void agregarPacienteAClinica(Long clinicaId, Long pacienteId);
    void eliminarPacienteDeClinica(Long clinicaId, Long pacienteId);
}