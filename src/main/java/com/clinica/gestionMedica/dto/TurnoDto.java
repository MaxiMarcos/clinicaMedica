package com.clinica.gestionMedica.dto;

import com.clinica.gestionMedica.enums.PresenciaEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TurnoDto {


    private int precioTotal;
    private int codigoTurno;
    private LocalDateTime fechaConsulta;
    private PresenciaEnum estado;
}

