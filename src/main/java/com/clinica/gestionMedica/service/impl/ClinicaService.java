package com.clinica.gestionMedica.service.impl;

import com.clinica.gestionMedica.dto.ClinicaRequestDto;
import com.clinica.gestionMedica.dto.ClinicaResponseDto;
import com.clinica.gestionMedica.entity.Clinica;
import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.excepciones.clinica.ClinicaNoEncontradaException;
import com.clinica.gestionMedica.excepciones.paciente.PacienteNoEncontradoException;
import com.clinica.gestionMedica.mapper.ClinicaMapper;
import com.clinica.gestionMedica.repository.ClinicaRepository;
import com.clinica.gestionMedica.repository.PacienteRepository;
import com.clinica.gestionMedica.service.IClinicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClinicaService implements IClinicaService {

    private final ClinicaRepository clinicaRepository;
    private final PacienteRepository pacienteRepository;
    private final ClinicaMapper clinicaMapper;

    @Override
    public ClinicaResponseDto crearClinica(ClinicaRequestDto clinicaRequestDto) {
        Clinica clinica = clinicaMapper.toEntity(clinicaRequestDto);
        clinicaRepository.save(clinica);
        return clinicaMapper.toResponse(clinica);
    }

    @Override
    public ClinicaResponseDto obtenerClinicaPorId(Long id) {
        Clinica clinica = clinicaRepository.findById(id)
                .orElseThrow(ClinicaNoEncontradaException::new);
        return clinicaMapper.toResponse(clinica);
    }

    @Override
    public List<ClinicaResponseDto> obtenerTodasLasClinicas() {
        return clinicaRepository.findAll().stream()
                .map(clinicaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ClinicaResponseDto actualizarClinica(Long id, ClinicaRequestDto clinicaRequestDto) {
        Clinica clinica = clinicaRepository.findById(id)
                .orElseThrow(ClinicaNoEncontradaException::new);
        clinica.setNombre(clinicaRequestDto.getNombre());
        clinica.setDireccion(clinicaRequestDto.getDireccion());
        clinica.setTelefono(clinicaRequestDto.getTelefono());
        clinicaRepository.save(clinica);
        return clinicaMapper.toResponse(clinica);
    }

    @Override
    public void eliminarClinica(Long id) {
        Clinica clinica = clinicaRepository.findById(id)
                .orElseThrow(ClinicaNoEncontradaException::new);
        clinicaRepository.delete(clinica);
    }

    @Override
    public void agregarPacienteAClinica(Long clinicaId, Long pacienteId) {
        Clinica clinica = clinicaRepository.findById(clinicaId)
                .orElseThrow(ClinicaNoEncontradaException::new);

        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(PacienteNoEncontradoException::new);

        Set <Paciente> pacientes = new HashSet();
        pacientes.add(paciente);

        clinica.setPacientes(pacientes);

        clinicaRepository.save(clinica);
    }

    @Override
    public void eliminarPacienteDeClinica(Long clinicaId, Long pacienteId) {
        Clinica clinica = clinicaRepository.findById(clinicaId)
                .orElseThrow(ClinicaNoEncontradaException::new);
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(PacienteNoEncontradoException::new);
        clinicaRepository.save(clinica);
    }
}