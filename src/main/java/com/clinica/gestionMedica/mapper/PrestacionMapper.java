package com.clinica.gestionMedica.mapper;

import com.clinica.gestionMedica.dto.MedicoResponseDto;
import com.clinica.gestionMedica.dto.PrestacionRequestDto;
import com.clinica.gestionMedica.dto.PrestacionResponseDto;
import com.clinica.gestionMedica.entity.Medico;
import com.clinica.gestionMedica.entity.Prestacion;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PrestacionMapper {

    public PrestacionResponseDto toResponse(Prestacion prestacion){

        return PrestacionResponseDto.builder()
                .tipo(prestacion.getTipoPrestacion())
                .descripcion(prestacion.getDescripcion())
                .precio(prestacion.getPrecio())
                .build();
    }


    public Prestacion toEntity(PrestacionRequestDto prestacionRequest){

        return Prestacion.builder()
                .tipoPrestacion(prestacionRequest.getTipoPrestacion())
                .descripcion(prestacionRequest.getDescripcion())
                .precio(prestacionRequest.getPrecio())
                .build();
    }

    public List<PrestacionResponseDto> toResponseList(List<Prestacion> prestaciones){
        List<PrestacionResponseDto> prestacionesResponse = new ArrayList<>();

        for(Prestacion p : prestaciones){
            prestacionesResponse.add(toResponse(p));
        }
        return prestacionesResponse;
    }

}
