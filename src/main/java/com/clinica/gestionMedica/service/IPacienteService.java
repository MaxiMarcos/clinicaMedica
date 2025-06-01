package com.clinica.gestionMedica.service;

import com.clinica.gestionMedica.dto.PacienteDto;
import com.clinica.gestionMedica.entity.Paciente;

import java.util.List;

public interface IPacienteService {

    PacienteDto crearPaciente(Paciente paciente);
    PacienteDto editarPaciente(Long id, Paciente paciente);
    PacienteDto traerPaciente(Long id);
    PacienteDto traerPacientePorDni(String dni);
    List<Paciente> traerPacientes();
    void eliminarPaciente(Long id);
    PacienteDto traerHistorial(Long id);

}
