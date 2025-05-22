package com.clinica.gestionMedica.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicoDto {

    @JsonIgnore //
    private Long id;
    private String apellido;
    private String nombre;
}
