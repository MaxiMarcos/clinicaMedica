package com.clinica.gestionMedica.dto;

import com.clinica.gestionMedica.enums.PrestacionTiposEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicoResponseDto {

    private Long id;
    private String apellido;
    private String nombre;
    private String dni;
    private String telefono;
    private String email;
    private PrestacionTiposEnum especializacion;
}
