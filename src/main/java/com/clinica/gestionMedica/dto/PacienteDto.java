package com.clinica.gestionMedica.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PacienteDto {

    // FALTA IMPLEMENTARLO EN EL HISTORIAL

    private String apellido;
    private String nombre;
    private String dni;
    private String telefono;
    private String email;
    private List<ReservaPacienteDto> historial;
}
