package com.clinica.gestionMedica.dto;

import com.clinica.gestionMedica.enums.TipoPrestacion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicoRequestDto {

    @NotBlank
    private String apellido;
    @NotBlank
    private String nombre;
    @NotNull
    private LocalDate fecha_nacimiento;
    @NotBlank
    private String dni;
    @NotBlank
    private String telefono;
    @NotBlank
    private String direccion;
    @NotBlank
    private String email;
    @NotNull
    private TipoPrestacion especializacion;
    @NotNull
    private Double sueldo;
    @NotNull
    private Long clinica;
}
