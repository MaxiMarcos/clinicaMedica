package com.clinica.gestionMedica.dto;

import com.clinica.gestionMedica.enums.ObraSocialEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PacienteResponseDto {

    private Long id;
    private String apellido;
    private String nombre;
    private String dni;
    private String telefono;
    private String email;
    private ObraSocialEnum obraSocial;
    private List<TurnoResponseDto> historial;
}
