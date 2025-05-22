package com.clinica.gestionMedica.mapper;

import com.clinica.gestionMedica.dto.MedicoDto;
import com.clinica.gestionMedica.dto.PacienteDto;
import com.clinica.gestionMedica.dto.PrestacionDto;
import com.clinica.gestionMedica.dto.ReservaDto;
import com.clinica.gestionMedica.entity.Medico;
import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.entity.Prestacion;
import com.clinica.gestionMedica.entity.Reserva;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReservaMapper {

    public MedicoDto conversionMedicoDto(Medico medico) {
        return new MedicoDto(medico.getId(), medico.getNombre(), medico.getApellido());
    }

    public ReservaDto conversionADto(Reserva reserva){


        List<PrestacionDto> prestacionesDto = reserva.getPrestaciones().stream()
                .map(prestacion -> new PrestacionDto(prestacion.getCodigoPrestacion(),
                        prestacion.getDescripcion(),prestacion.getPrecio(),
                        prestacion.getTipo(),
                        prestacion.getEstado(),
                        prestacion.getFechaConsulta(),
                        conversionMedicoDto(prestacion.getMedico())))
                .collect(Collectors.toList());

        return ReservaDto.builder()
                //.estadoPresencia(reserva.getEstadoPresencia())
                .estadoPago(reserva.getEstadoPago())
                .precioTotal(reserva.getPrecioTotal())
                .prestaciones(prestacionesDto)
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
