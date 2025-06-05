package com.clinica.gestionMedica.mapper;

import com.clinica.gestionMedica.dto.MedicoRequestDto;
import com.clinica.gestionMedica.dto.MedicoResponseDto;
import com.clinica.gestionMedica.dto.PacienteResponseDto;
import com.clinica.gestionMedica.entity.Medico;
import com.clinica.gestionMedica.entity.Paciente;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MedicoMapper {

    public Medico convertirRequestAMedico(MedicoRequestDto medicoRequest){

        return Medico.builder()
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
    }

    public Medico convertirResponseAMedico(MedicoResponseDto medicoResponse){

        return Medico.builder()
                .email(medicoResponse.getEmail())
                .dni(medicoResponse.getDni())
                .apellido(medicoResponse.getApellido())
                .nombre(medicoResponse.getNombre())
                .especializacion(medicoResponse.getEspecializacion())
                .telefono(medicoResponse.getTelefono())
                .build();
    }

    public MedicoResponseDto convertirMedicoAResponse(Medico medico){

        return MedicoResponseDto.builder()
                .id(medico.getId())
                .email(medico.getEmail())
                .dni(medico.getDni())
                .apellido(medico.getApellido())
                .nombre(medico.getNombre())
                .especializacion(medico.getEspecializacion())
                .telefono(medico.getTelefono())
                .build();
    }

    public List<MedicoResponseDto> convertirAMedicosResponse(List<Medico> medicos){

        List<MedicoResponseDto> medicosDto = new ArrayList<>();
        for(Medico m : medicos){
            medicosDto.add(convertirMedicoAResponse(m));
        }
        return medicosDto;
    }

}
