package com.clinica.gestionMedica.mapper;

import com.clinica.gestionMedica.dto.PacienteRequestDto;
import com.clinica.gestionMedica.dto.PacienteResponseDto;
import com.clinica.gestionMedica.entity.Paciente;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class PacienteMapper {

    private final TurnoMapper TurnoMapper;

    public PacienteMapper(TurnoMapper TurnoMapper) {
        this.TurnoMapper = TurnoMapper;
    }

    public PacienteResponseDto toResponse(Paciente paciente){

        return PacienteResponseDto.builder()
                .dni(paciente.getDni())
                .email(paciente.getEmail())
                .nombre(paciente.getNombre())
                .apellido(paciente.getApellido())
                .telefono(paciente.getTelefono())
                .historial(TurnoMapper.toResponseList(
                        Optional.ofNullable(paciente.getListaTurnos()).orElse(Collections.emptyList()) // +++
                ))
                .build();
    }

    public Paciente toEntity(PacienteRequestDto pacienteRequest){

        return Paciente.builder()
                .dni(pacienteRequest.getDni())
                .email(pacienteRequest.getEmail())
                .nombre(pacienteRequest.getNombre())
                .apellido(pacienteRequest.getApellido())
                .fecha_nacimiento(pacienteRequest.getFecha_nacimiento())
                .direccion(pacienteRequest.getDireccion())
                .obraSocial(pacienteRequest.getObraSocial())
                .telefono(pacienteRequest.getTelefono())
                .listaTurnos(TurnoMapper.conversionResponsesATurnos(pacienteRequest.getHistorial()))
                .build();
    }


    public List<PacienteResponseDto> toResponseList(List<Paciente> pacientes){

        List<PacienteResponseDto> pacientesDto = new ArrayList<>();
        for(Paciente p : pacientes){
            pacientesDto.add(toResponse(p));
        }
        return pacientesDto;
    }
}
