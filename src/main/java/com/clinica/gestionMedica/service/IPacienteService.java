package com.clinica.gestionMedica.service;

import com.clinica.gestionMedica.dto.PacienteRequestDto;
import com.clinica.gestionMedica.dto.PacienteResponseDto;
import com.clinica.gestionMedica.dto.TurnoResponseDto;
import com.clinica.gestionMedica.entity.Paciente;

import java.util.List;

public interface IPacienteService {

    PacienteResponseDto crearPaciente(PacienteRequestDto pacienteRequest);
    PacienteResponseDto editarPaciente(Long id, PacienteRequestDto pacienteRequest);
    PacienteResponseDto traerPaciente(Long id);
    PacienteResponseDto traerPacientePorDni(String dni);
    List<PacienteResponseDto> traerPacientes();
    void eliminarPaciente(Long id);
    List<TurnoResponseDto> traerHistorial(String dni);

}
