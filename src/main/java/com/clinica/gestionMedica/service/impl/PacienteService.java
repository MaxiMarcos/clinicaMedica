package com.clinica.gestionMedica.service.impl;

import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.repository.PacienteRepository;
import com.clinica.gestionMedica.service.IPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService implements IPacienteService {

    @Autowired
    PacienteRepository pacienteRepo;

    @Override
    public Paciente crearPaciente(Paciente paciente) {
        return pacienteRepo.save(paciente);
    }

    @Override
    public Paciente editarPaciente(Long id, Paciente paciente) {
        return null;
    }

    @Override
    public Paciente traerPaciente(Long id) {
        return pacienteRepo.findById(id).orElse(null);
    }

    @Override
    public List<Paciente> traerPacientes() {
        return pacienteRepo.findAll();
    }

    @Override
    public void eliminarPaciente(Long id) {

        pacienteRepo.deleteById(id);
    }
}
