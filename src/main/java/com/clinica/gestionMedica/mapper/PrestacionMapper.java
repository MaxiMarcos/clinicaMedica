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

    public PrestacionResponseDto conversionPrestacionAResponse(Prestacion prestacion){

        return PrestacionResponseDto.builder()
                .tipo(prestacion.getTipo())
                .descripcion(prestacion.getDescripcion())
                .precio(prestacion.getPrecio())
                .build();
    }

    public PrestacionResponseDto conversionRequestAResponse(PrestacionRequestDto prestacionRequest){

        return PrestacionResponseDto.builder()
                .tipo(prestacionRequest.getTipo())
                .descripcion(prestacionRequest.getDescripcion())
                .precio(prestacionRequest.getPrecio())
                .build();
    }

    public Prestacion conversionRequestAPrestacion(PrestacionRequestDto prestacionRequest){

        return Prestacion.builder()
                .tipo(prestacionRequest.getTipo())
                .descripcion(prestacionRequest.getDescripcion())
                .precio(prestacionRequest.getPrecio())
                .build();
    }

    public List<PrestacionResponseDto> conversionPrestacionesAResponse(List<Prestacion> prestaciones){
        List<PrestacionResponseDto> prestacionesResponse = new ArrayList<>();

        for(Prestacion p : prestaciones){
            prestacionesResponse.add(conversionPrestacionAResponse(p));
        }
        return prestacionesResponse;
    }

}
