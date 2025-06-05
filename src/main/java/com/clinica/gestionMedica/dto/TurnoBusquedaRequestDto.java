package com.clinica.gestionMedica.dto;

import com.clinica.gestionMedica.enums.PrestacionTiposEnum;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TurnoBusquedaRequestDto {

    @NotNull(message = "El ID del paciente no puede ser nulo")
    private Long pacienteId;

    @NotNull(message = "El tipo de prestación es obligatorio")
    private PrestacionTiposEnum tipo;
}
