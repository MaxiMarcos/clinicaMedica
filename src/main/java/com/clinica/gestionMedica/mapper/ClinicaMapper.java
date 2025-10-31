package com.clinica.gestionMedica.mapper;

import com.clinica.gestionMedica.dto.ClinicaRequestDto;
import com.clinica.gestionMedica.dto.ClinicaResponseDto;
import com.clinica.gestionMedica.entity.Clinica;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ClinicaMapper {

    public Clinica toEntity(ClinicaRequestDto clinicaRequestDto) {
        return Clinica.builder()
                .nombre(clinicaRequestDto.getNombre())
                .direccion(clinicaRequestDto.getDireccion())
                .telefono(clinicaRequestDto.getTelefono())
                .build();
    }

    public ClinicaResponseDto toResponse(Clinica clinica) {
        return ClinicaResponseDto.builder()
                .id(clinica.getId())
                .nombre(clinica.getNombre())
                .direccion(clinica.getDireccion())
                .telefono(clinica.getTelefono())
                .build();
    }

    public Set<ClinicaResponseDto> toResponseList(Set<Clinica> clinicas) {
        Set<ClinicaResponseDto> clinicasDto = new HashSet<>();
        for (Clinica c : clinicas) {
            clinicasDto.add(toResponse(c));
        }
        return clinicasDto;
    }

}