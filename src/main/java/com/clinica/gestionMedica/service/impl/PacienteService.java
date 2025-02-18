package com.clinica.gestionMedica.service.impl;

import com.clinica.gestionMedica.dto.PacienteDto;
import com.clinica.gestionMedica.dto.PrestacionDto;
import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.entity.Prestacion;
import com.clinica.gestionMedica.entity.Reserva;
import com.clinica.gestionMedica.mapper.ReservaMapper;
import com.clinica.gestionMedica.repository.PacienteRepository;
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
    ReservaMapper reservaMapper;

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

    @Override
    public PacienteDto traerHistorial(Long id) {
        Paciente paciente = pacienteRepo.findById(id).orElse(null);
        List<Reserva> reservas = paciente.getListaReservas();

        PacienteDto pacienteDto = new PacienteDto();
        pacienteDto.setHistorial(reservaMapper.ListaHistorialDto(reservas));
        pacienteDto.setNombre(paciente.getNombre());
        pacienteDto.setDni(paciente.getDni());
        pacienteDto.setApellido(paciente.getApellido());
        pacienteDto.setEmail(paciente.getEmail());
        pacienteDto.setTelefono(paciente.getTelefono());

        return pacienteDto;
    }



}
