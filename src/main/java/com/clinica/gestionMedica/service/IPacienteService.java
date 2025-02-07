package com.clinica.gestionMedica.service;

import com.clinica.gestionMedica.entity.Paciente;

import java.util.List;

public interface IPacienteService {

    Paciente crearPaciente(Paciente paciente);
    Paciente editarPaciente(Long id, Paciente paciente);
    Paciente traerPaciente(Long id);
    List<Paciente> traerPacientes();
    void eliminarPaciente(Long id);
}
