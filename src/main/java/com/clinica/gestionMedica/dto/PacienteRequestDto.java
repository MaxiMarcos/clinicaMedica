package com.clinica.gestionMedica.dto;

import com.clinica.gestionMedica.enums.ObraSocialEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PacienteRequestDto {

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
    private ObraSocialEnum obraSocial;
    //@NotNull
    private List<TurnoResponseDto> historial;
}
