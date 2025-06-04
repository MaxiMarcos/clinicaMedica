package com.clinica.gestionMedica.mapper;

import com.clinica.gestionMedica.dto.TurnoDto;
import com.clinica.gestionMedica.entity.Turno;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TurnoMapper {

    public TurnoDto conversionADto(Turno Turno){


        return TurnoDto.builder()
                .estado(Turno.getEstado())
                .codigoTurno(Turno.getCodigoTurno())
                .fechaConsulta(Turno.getFechaConsulta())
                .build();
    }



    public List<TurnoDto> ListaHistorialDto(List<Turno> turnos){

        List<TurnoDto> TurnoDto = new ArrayList<>();
        for(Turno r : turnos){
            TurnoDto.add(conversionADto(r));
        }
        return TurnoDto;
    }


}
