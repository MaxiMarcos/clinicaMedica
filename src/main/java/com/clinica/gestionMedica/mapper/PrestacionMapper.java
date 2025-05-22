package com.clinica.gestionMedica.mapper;

import com.clinica.gestionMedica.dto.MedicoDto;
import com.clinica.gestionMedica.dto.PacienteDto;
import com.clinica.gestionMedica.dto.PrestacionDto;
import com.clinica.gestionMedica.entity.Medico;
import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.entity.Prestacion;
import org.springframework.stereotype.Component;

@Component
public class PrestacionMapper {

    public MedicoDto conversionMedicoDto(Medico medico) {
        return new MedicoDto(medico.getId(), medico.getNombre(), medico.getApellido());
    }

    public Medico conversionMedico(MedicoDto medicoDto) {
        Medico medico = new Medico();
        medico.setId(medicoDto.getId()); // ahora lo tenés disponible
        return medico;
    }


    public PrestacionDto conversionAPrestacionDto(Prestacion prestacion){

        return PrestacionDto.builder()
                .medicoDto(conversionMedicoDto(prestacion.getMedico()))
                .codigoPrestacion(prestacion.getCodigoPrestacion())
                .tipo(prestacion.getTipo())
                .descripcion(prestacion.getDescripcion())
                .precio(prestacion.getPrecio())
                .estado(prestacion.getEstado())
                .fechaConsulta(prestacion.getFechaConsulta())
                .build();
    }

    public Prestacion conversionAPrestacion(PrestacionDto prestacionDto){

        return Prestacion.builder()
                .medico(conversionMedico(prestacionDto.getMedicoDto()))
                .codigoPrestacion(prestacionDto.getCodigoPrestacion())
                .tipo(prestacionDto.getTipo())
                .descripcion(prestacionDto.getDescripcion())
                .precio(prestacionDto.getPrecio())
                .estado(prestacionDto.getEstado())
                .fechaConsulta(prestacionDto.getFechaConsulta())
                .build();
    }
}
