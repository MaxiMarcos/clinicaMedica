package com.clinica.gestionMedica.service.impl;

import com.clinica.gestionMedica.entity.Medico;
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

        Paciente nuevoPaciente = this.traerPaciente(id);
        nuevoPaciente.setApellido(paciente.getApellido());
        nuevoPaciente.setNombre(paciente.getNombre());
        nuevoPaciente.setFecha_nacimiento(paciente.getFecha_nacimiento());
        nuevoPaciente.setDni(paciente.getDni());
        nuevoPaciente.setTelefono(paciente.getTelefono());
        nuevoPaciente.setDireccion(paciente.getDireccion());
        nuevoPaciente.setEmail(paciente.getEmail());
        nuevoPaciente.setListaReservas(paciente.getListaReservas());

        return pacienteRepo.save(nuevoPaciente);
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
