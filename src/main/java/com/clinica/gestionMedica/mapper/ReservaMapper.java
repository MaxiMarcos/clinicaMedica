package com.clinica.gestionMedica.mapper;

import com.clinica.gestionMedica.dto.PacienteDto;
import com.clinica.gestionMedica.dto.PrestacionDto;
import com.clinica.gestionMedica.dto.ReservaPacienteDto;
import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.entity.Reserva;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReservaMapper {

    public PacienteDto conversionAPacienteDto(Paciente paciente){

        return PacienteDto.builder()
                .dni(paciente.getDni())
                .email(paciente.getEmail())
                .nombre(paciente.getNombre())
                .apellido(paciente.getApellido())
                .telefono(paciente.getTelefono())
                .build();
    }

    public ReservaPacienteDto conversionADto(Reserva reserva){

        List<PrestacionDto> prestacionesDto = reserva.getPrestaciones().stream()
                .map(prestacion -> new PrestacionDto(prestacion.getTipo(), prestacion.getEstado(),  prestacion.getFechaConsulta(), prestacion.getMedico()))
                .collect(Collectors.toList());

        return ReservaPacienteDto.builder()
                .estadoPresencia(reserva.getEstadoPresencia())
                .estadoPago(reserva.getEstadoPago())
                .precioTotal(reserva.getPrecioTotal())
                .prestaciones(prestacionesDto)
                .build();
    }



    public List<ReservaPacienteDto> ListaHistorialDto(List<Reserva> reservas){

        List<ReservaPacienteDto> reservaDto = new ArrayList<>();
        for(Reserva r : reservas){
            reservaDto.add(conversionADto(r));
        }
        return reservaDto;
    }


}
