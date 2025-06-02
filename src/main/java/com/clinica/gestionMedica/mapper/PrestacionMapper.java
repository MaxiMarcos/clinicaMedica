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
                .tipo(prestacion.getTipo())
                .descripcion(prestacion.getDescripcion())
                .precio(prestacion.getPrecio())
                .build();
    }

    public Prestacion conversionAPrestacion(PrestacionDto prestacionDto){

        return Prestacion.builder()
                .tipo(prestacionDto.getTipo())
                .descripcion(prestacionDto.getDescripcion())
                .precio(prestacionDto.getPrecio())
                .build();
    }
}
