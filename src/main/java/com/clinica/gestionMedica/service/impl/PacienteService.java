package com.clinica.gestionMedica.service.impl;

import com.clinica.gestionMedica.dto.PacienteRequestDto;
import com.clinica.gestionMedica.dto.PacienteResponseDto;
import com.clinica.gestionMedica.dto.TurnoResponseDto;
import com.clinica.gestionMedica.entity.Medico;
import com.clinica.gestionMedica.dto.ClinicaResponseDto;
import com.clinica.gestionMedica.mapper.ClinicaMapper;
import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.entity.Turno;
import com.clinica.gestionMedica.excepciones.medico.MedicoNoEncontradoException;
import com.clinica.gestionMedica.excepciones.medico.MedicoYaExisteException;
import com.clinica.gestionMedica.excepciones.paciente.PacienteNoEncontradoException;
import com.clinica.gestionMedica.excepciones.paciente.PacienteYaExisteException;
import com.clinica.gestionMedica.mapper.PacienteMapper;
import com.clinica.gestionMedica.mapper.TurnoMapper;
import com.clinica.gestionMedica.repository.PacienteRepository;
import com.clinica.gestionMedica.service.IPacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PacienteService implements IPacienteService {

    private final PacienteRepository pacienteRepo;
private final PacienteMapper pacienteMapper;
private final TurnoMapper turnoMapper;
private final ClinicaMapper clinicaMapper;

    @Override
    public PacienteResponseDto crearPaciente(PacienteRequestDto pacienteRequest) {

        validarPacienteExistente(pacienteRequest.getDni());

        Paciente paciente = pacienteMapper.toEntity(pacienteRequest);
        pacienteRepo.save(paciente);

        return pacienteMapper.toResponse(paciente);
    }


    @Override
    public PacienteResponseDto editarPaciente(Long id, PacienteRequestDto pacienteRequest) {

        Paciente paciente = pacienteRepo.findById(id)
                .orElseThrow(PacienteNoEncontradoException::new);

        actualizarPaciente(pacienteRequest, paciente);

        pacienteRepo.save(paciente);
        return pacienteMapper.toResponse(paciente);
    }

    @Override
    public PacienteResponseDto traerPaciente(Long id) {
        Paciente paciente = pacienteRepo.findById(id)
                .orElseThrow(PacienteNoEncontradoException::new);

        return pacienteMapper.toResponse(paciente);
    }

    @Override
    public PacienteResponseDto traerPacientePorDni(String dni) {
        return pacienteMapper.toResponse(pacienteRepo.findByDni(dni));
    }

    @Override
    public List<PacienteResponseDto> traerPacientes() {
        return pacienteMapper.toResponseList(pacienteRepo.findAll());
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
        List<TurnoResponseDto> turnosResponse = turnoMapper.toResponseList(turnos);
        return turnosResponse;
    }

    private void actualizarPaciente(PacienteRequestDto pacienteRequest, Paciente paciente){

        if(pacienteRequest.getApellido() != null) paciente.setApellido(pacienteRequest.getApellido());
        if(pacienteRequest.getNombre() != null) paciente.setNombre(pacienteRequest.getNombre());
        if(pacienteRequest.getFecha_nacimiento() != null) paciente.setFecha_nacimiento(pacienteRequest.getFecha_nacimiento());
        if(pacienteRequest.getDni() != null) paciente.setDni(pacienteRequest.getDni());
        if(pacienteRequest.getTelefono() != null) paciente.setTelefono(pacienteRequest.getTelefono());
        if(pacienteRequest.getDireccion() != null) paciente.setDireccion(pacienteRequest.getDireccion());
        if(pacienteRequest.getEmail() !=null) paciente.setEmail(pacienteRequest.getEmail());
    }

    private void validarPacienteExistente(String dni){

        Paciente paciente = pacienteRepo.findByDni(dni);
        if (paciente != null) {
            throw new MedicoYaExisteException("Ya existe un paciente creado con este DNI.");
        }
    }



    @Override
    public Set<ClinicaResponseDto> obtenerClinicasPorPaciente(Long pacienteId) {
        Paciente paciente = pacienteRepo.findById(pacienteId)
                .orElseThrow(PacienteNoEncontradoException::new);
        return clinicaMapper.toResponseList(paciente.getClinicas());
    }
}
