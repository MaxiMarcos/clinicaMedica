package com.clinica.gestionMedica.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PacienteDto {

    private String apellido;
    private String nombre;
    private String dni;
    private String telefono;
    private String email;
    private List<TurnoDto> historial;
}
