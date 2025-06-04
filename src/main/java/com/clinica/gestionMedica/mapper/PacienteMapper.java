package com.clinica.gestionMedica.mapper;

import com.clinica.gestionMedica.dto.PacienteDto;
import com.clinica.gestionMedica.dto.TurnoDto;
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

    public PacienteDto conversionAPacienteDto(Paciente paciente){

        return PacienteDto.builder()
                .dni(paciente.getDni())
                .email(paciente.getEmail())
                .nombre(paciente.getNombre())
                .apellido(paciente.getApellido())
                .telefono(paciente.getTelefono())
                .historial(TurnoMapper.ListaHistorialDto(
                        Optional.ofNullable(paciente.getListaTurnos()).orElse(Collections.emptyList())
                ))
                .build();
    }


    public List<PacienteDto> conversionPacientesDto(List<Paciente> pacientes){

        List<PacienteDto> pacientesDto = new ArrayList<>();
        for(Paciente p : pacientes){
            pacientesDto.add(conversionAPacienteDto(p));
        }
        return pacientesDto;
    }
}
