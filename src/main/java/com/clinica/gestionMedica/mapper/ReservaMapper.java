package com.clinica.gestionMedica.mapper;

import com.clinica.gestionMedica.dto.ReservaDto;
import com.clinica.gestionMedica.entity.Reserva;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReservaMapper {

    public ReservaDto conversionADto(Reserva reserva){


        return ReservaDto.builder()
                .estado(reserva.getEstado())
                .precioTotal(reserva.getPrecioTotal())
                .codigoTurno(reserva.getCodigoTurno())
                .fechaConsulta(reserva.getFechaConsulta())
                .build();
    }



    public List<ReservaDto> ListaHistorialDto(List<Reserva> reservas){

        List<ReservaDto> reservaDto = new ArrayList<>();
        for(Reserva r : reservas){
            reservaDto.add(conversionADto(r));
        }
        return reservaDto;
    }


}
