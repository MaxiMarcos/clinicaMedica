package com.clinica.gestionMedica.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicoDto {

    private String apellido;
    private String nombre;
}
