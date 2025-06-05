package com.clinica.gestionMedica.dto;

import com.clinica.gestionMedica.enums.PresenciaEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TurnoResponseDto {

    private Long id;
    private int precioTotal;
    private int codigoTurno;
    private LocalDateTime fechaConsulta;
    private PresenciaEnum estado;
}

