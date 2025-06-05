package com.clinica.gestionMedica.service.impl;

import com.clinica.gestionMedica.dto.PacienteRequestDto;
import com.clinica.gestionMedica.dto.PacienteResponseDto;
import com.clinica.gestionMedica.dto.TurnoResponseDto;
import com.clinica.gestionMedica.entity.Medico;
import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.entity.Turno;
import com.clinica.gestionMedica.excepciones.medico.MedicoNoEncontradoException;
import com.clinica.gestionMedica.excepciones.paciente.PacienteNoEncontradoException;
import com.clinica.gestionMedica.mapper.PacienteMapper;
import com.clinica.gestionMedica.mapper.TurnoMapper;
import com.clinica.gestionMedica.repository.PacienteRepository;
import com.clinica.gestionMedica.service.IPacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PacienteService implements IPacienteService {

    private final PacienteRepository pacienteRepo;
    private final PacienteMapper pacienteMapper;
    private final TurnoMapper turnoMapper;

    @Override
    public PacienteResponseDto crearPaciente(PacienteRequestDto pacienteRequest) {

        Paciente pacientePorDni = pacienteRepo.findByDni(pacienteRequest.getDni());
        if (pacientePorDni != null) {
            throw new IllegalStateException("Ya existe un paciente creado con este DNI." );
        }
        Paciente paciente = pacienteMapper.conversionRequestAPaciente(pacienteRequest);
        pacienteRepo.save(paciente);

        return pacienteMapper.conversionPacienteAResponseDto(paciente);
    }


    @Override
    public PacienteResponseDto editarPaciente(Long id, PacienteRequestDto pacienteRequest) {

        Paciente paciente = pacienteRepo.findById(id)
                .orElseThrow(() -> new PacienteNoEncontradoException("Paciente no encontrado con id: " + id));

        paciente.setApellido(pacienteRequest.getApellido());
        paciente.setNombre(pacienteRequest.getNombre());
        paciente.setFecha_nacimiento(pacienteRequest.getFecha_nacimiento());
        paciente.setDni(pacienteRequest.getDni());
        paciente.setTelefono(pacienteRequest.getTelefono());
        paciente.setDireccion(pacienteRequest.getDireccion());
        paciente.setEmail(pacienteRequest.getEmail());

        pacienteRepo.save(paciente);
        return pacienteMapper.conversionPacienteAResponseDto(paciente);
    }

    @Override
    public PacienteResponseDto traerPaciente(Long id) {
        Paciente paciente = pacienteRepo.findById(id)
                .orElseThrow(() -> new PacienteNoEncontradoException("Paciente no encontrado con id: " + id));

        return pacienteMapper.conversionPacienteAResponseDto(paciente);
    }

    @Override
    public PacienteResponseDto traerPacientePorDni(String dni) {
        return pacienteMapper.conversionPacienteAResponseDto(pacienteRepo.findByDni(dni));
    }

    @Override
    public List<PacienteResponseDto> traerPacientes() {
        return pacienteMapper.conversionPacientesAResponse(pacienteRepo.findAll());
    }

    @Override
    public void eliminarPaciente(Long id) {
        Paciente paciente = pacienteRepo.findById(id)
                .orElseThrow(() -> new PacienteNoEncontradoException("Paciente no encontrado con id: " + id));
        pacienteRepo.deleteById(paciente.getId());
    }

    @Override
    public List<TurnoResponseDto> traerHistorial(Long id) {
        Paciente paciente = pacienteRepo.findById(id).orElse(null);
        List<Turno> turnos = paciente.getListaTurnos();
        List<TurnoResponseDto> turnosResponse = turnoMapper.conversionTurnosAResponse(turnos);
        return turnosResponse;
    }



}
