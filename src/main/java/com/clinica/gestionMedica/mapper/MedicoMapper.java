package com.clinica.gestionMedica.mapper;

import com.clinica.gestionMedica.dto.MedicoRequestDto;
import com.clinica.gestionMedica.dto.MedicoResponseDto;
import com.clinica.gestionMedica.dto.PacienteResponseDto;
import com.clinica.gestionMedica.entity.Clinica;
import com.clinica.gestionMedica.entity.Medico;
import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.repository.ClinicaRepository;
import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MedicoMapper {

    private final ClinicaRepository clinicaRepository;

    public MedicoMapper(ClinicaRepository clinicaRepository) {
        this.clinicaRepository = clinicaRepository;
    }

    public Medico toEntity(MedicoRequestDto medicoRequest){

        Medico medico = Medico.builder()
                .email(medicoRequest.getEmail())
                .dni(medicoRequest.getDni())
                .apellido(medicoRequest.getApellido())
                .nombre(medicoRequest.getNombre())
                .direccion(medicoRequest.getDireccion())
                .sueldo(medicoRequest.getSueldo())
                .especializacion(medicoRequest.getEspecializacion())
                .telefono(medicoRequest.getTelefono())
                .fecha_nacimiento(medicoRequest.getFecha_nacimiento())
                .build();

        if (medicoRequest.getClinica() != null) {
            Optional<Clinica> clinicaOptional = clinicaRepository.findById(medicoRequest.getClinica());
            clinicaOptional.ifPresent(medico::setClinica);
        }

        return medico;
    }


    public MedicoResponseDto toResponse(Medico medico){

        return MedicoResponseDto.builder()
                .id(medico.getId())
                .email(medico.getEmail())
                .dni(medico.getDni())
                .apellido(medico.getApellido())
                .nombre(medico.getNombre())
                .especializacion(medico.getEspecializacion())
                .telefono(medico.getTelefono())
                .clinica(medico.getClinica() != null ? medico.getClinica().getId() : null)
                .build();
    }

    public List<MedicoResponseDto> toResponseList(List<Medico> medicos){

        List<MedicoResponseDto> medicosDto = new ArrayList<>();
        for(Medico m : medicos){
            medicosDto.add(toResponse(m));
        }
        return medicosDto;
    }
}
