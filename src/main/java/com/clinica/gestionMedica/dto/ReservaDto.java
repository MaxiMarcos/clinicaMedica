package com.clinica.gestionMedica.dto;

import com.clinica.gestionMedica.enums.PresenciaEnum;
import com.clinica.gestionMedica.enums.ReservaEstadoEnum;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaDto {


    private int precioTotal;
    private int codigoTurno;
    private LocalDateTime fechaConsulta;
    private PresenciaEnum estado;
}

