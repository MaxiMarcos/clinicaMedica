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
                .orElseThrow(PacienteNoEncontradoException::new);

        if(pacienteRequest.getApellido() != null) paciente.setApellido(pacienteRequest.getApellido());
        if(pacienteRequest.getNombre() != null) paciente.setNombre(pacienteRequest.getNombre());
        if(pacienteRequest.getFecha_nacimiento() != null) paciente.setFecha_nacimiento(pacienteRequest.getFecha_nacimiento());
        if(pacienteRequest.getDni() != null) paciente.setDni(pacienteRequest.getDni());
        if(pacienteRequest.getTelefono() != null) paciente.setTelefono(pacienteRequest.getTelefono());
        if(pacienteRequest.getDireccion() != null) paciente.setDireccion(pacienteRequest.getDireccion());
        if(pacienteRequest.getEmail() !=null) paciente.setEmail(pacienteRequest.getEmail());

        pacienteRepo.save(paciente);
        return pacienteMapper.conversionPacienteAResponseDto(paciente);
    }

    @Override
    public PacienteResponseDto traerPaciente(Long id) {
        Paciente paciente = pacienteRepo.findById(id)
                .orElseThrow(PacienteNoEncontradoException::new);

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
                .orElseThrow(PacienteNoEncontradoException::new);
        pacienteRepo.deleteById(paciente.getId());
    }

    @Override
    public List<TurnoResponseDto> traerHistorial(String dni) {
        Paciente paciente = pacienteRepo.findByDni(dni);
        List<Turno> turnos = paciente.getListaTurnos();
        List<TurnoResponseDto> turnosResponse = turnoMapper.conversionTurnosAResponse(turnos);
        return turnosResponse;
    }



}
