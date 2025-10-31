package com.clinica.gestionMedica.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClinicaResponseDto {
    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
}