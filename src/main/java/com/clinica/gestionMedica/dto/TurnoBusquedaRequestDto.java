package com.clinica.gestionMedica.dto;

import com.clinica.gestionMedica.entity.Medico;
import com.clinica.gestionMedica.enums.TipoPrestacion;
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

    @NotNull(message = "El medicoId obligatorio")
    private Long medicoId;
}
