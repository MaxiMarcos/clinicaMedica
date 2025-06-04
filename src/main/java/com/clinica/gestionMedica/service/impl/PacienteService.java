package com.clinica.gestionMedica.service.impl;

import com.clinica.gestionMedica.dto.PacienteDto;
import com.clinica.gestionMedica.dto.PrestacionDto;
import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.entity.Prestacion;
import com.clinica.gestionMedica.entity.Turno;
import com.clinica.gestionMedica.mapper.PacienteMapper;
import com.clinica.gestionMedica.mapper.TurnoMapper;
import com.clinica.gestionMedica.repository.PacienteRepository;
import com.clinica.gestionMedica.security.enumRole.RoleName;
import com.clinica.gestionMedica.service.IPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PacienteService implements IPacienteService {

    @Autowired
    PacienteRepository pacienteRepo;
    @Autowired
    PacienteMapper pacienteMapper;
    @Autowired
    TurnoMapper turnoMapper;

    @Override
    public PacienteDto crearPaciente(Paciente paciente) {

        Paciente pacientePorDni = pacienteRepo.findByDni(paciente.getDni());
        if (pacientePorDni != null) {
            throw new IllegalStateException("Ya existe un paciente creado con este DNI." );
        }
        PacienteDto pacienteDto = pacienteMapper.conversionAPacienteDto(paciente);
        pacienteRepo.save(paciente);
        return pacienteDto;
    }


    @Override
    public PacienteDto editarPaciente(Long id, Paciente paciente) {

        Paciente nuevoPaciente = pacienteRepo.findById(id).orElse(null);
        nuevoPaciente.setApellido(paciente.getApellido());
        nuevoPaciente.setNombre(paciente.getNombre());
        nuevoPaciente.setFecha_nacimiento(paciente.getFecha_nacimiento());
        nuevoPaciente.setDni(paciente.getDni());
        nuevoPaciente.setTelefono(paciente.getTelefono());
        nuevoPaciente.setDireccion(paciente.getDireccion());
        nuevoPaciente.setEmail(paciente.getEmail());

        pacienteRepo.save(nuevoPaciente);
        return pacienteMapper.conversionAPacienteDto(nuevoPaciente);
    }

    @Override
    public PacienteDto traerPaciente(Long id) {
        Paciente paciente = pacienteRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente con ID " + id + " no encontrado"));

        PacienteDto pacienteDto = pacienteMapper.conversionAPacienteDto(paciente);
        return pacienteDto;
    }

    @Override
    public PacienteDto traerPacientePorDni(String dni) {
        PacienteDto pacienteDto = pacienteMapper.conversionAPacienteDto(pacienteRepo.findByDni(dni));
        return pacienteDto;
    }

    @Override
    public List<Paciente> traerPacientes() {
        return pacienteRepo.findAll();
    }

    @Override
    public void eliminarPaciente(Long id) {

        pacienteRepo.deleteById(id);
    }

    @Override
    public PacienteDto traerHistorial(Long id) {
        Paciente paciente = pacienteRepo.findById(id).orElse(null);
        List<Turno> turnos = paciente.getListaTurnos();

        PacienteDto pacienteDto = new PacienteDto();
        pacienteDto.setHistorial(turnoMapper.ListaHistorialDto(turnos));
        pacienteDto.setNombre(paciente.getNombre());
        pacienteDto.setDni(paciente.getDni());
        pacienteDto.setApellido(paciente.getApellido());
        pacienteDto.setEmail(paciente.getEmail());
        pacienteDto.setTelefono(paciente.getTelefono());

        return pacienteDto;
    }



}
