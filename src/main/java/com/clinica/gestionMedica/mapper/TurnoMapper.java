package com.clinica.gestionMedica.mapper;

import com.clinica.gestionMedica.dto.TurnoRequestDto;
import com.clinica.gestionMedica.dto.TurnoResponseDto;
import com.clinica.gestionMedica.entity.Turno;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TurnoMapper {

    public TurnoResponseDto conversionTurnoAResponse(Turno turno){


        return TurnoResponseDto.builder()
                .estado(turno.getEstado())
                .codigoTurno(turno.getCodigoTurno())
                .fechaConsulta(turno.getFechaConsulta())
                .build();
    }

    public Turno conversionResponseATurno(TurnoResponseDto turnoResponseDto){


        return Turno.builder()
                .estado(turnoResponseDto.getEstado())
                .codigoTurno(turnoResponseDto.getCodigoTurno())
                .fechaConsulta(turnoResponseDto.getFechaConsulta())
                .build();
    }

    public Turno conversionRequestATurno(TurnoRequestDto turnoRequest){

        return Turno.builder()
                .estado(turnoRequest.getEstado())
                .medico(turnoRequest.getMedico())
                .prestacion(turnoRequest.getPrestacion())
                .paciente(turnoRequest.getPaciente())
                .codigoTurno(turnoRequest.getCodigoTurno())
                .fechaConsulta(turnoRequest.getFechaConsulta())
                .build();
    }


    public List<TurnoResponseDto> conversionTurnosAResponse(List<Turno> turnos){

        List<TurnoResponseDto> turnoResponseDto = new ArrayList<>();
        for(Turno r : turnos){
            turnoResponseDto.add(conversionTurnoAResponse(r));
        }
        return turnoResponseDto;
    }

    public List<Turno> conversionResponsesATurnos(List<TurnoResponseDto> turnosResponse){

        if (turnosResponse == null) return new ArrayList<>();

        List<Turno> turnos= new ArrayList<>();
        for(TurnoResponseDto t : turnosResponse){
            turnos.add(conversionResponseATurno(t));
        }
        return turnos;
    }


}
