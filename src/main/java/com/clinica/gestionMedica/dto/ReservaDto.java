package com.clinica.gestionMedica.dto;

import com.clinica.gestionMedica.enums.PresenciaEnum;
import com.clinica.gestionMedica.enums.ReservaEstadoEnum;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaDto {


    private Double precioTotal = 0.0;
    private ReservaEstadoEnum estadoPago = ReservaEstadoEnum.PENDIENTE;
    private PresenciaEnum estadoPresencia = PresenciaEnum.RESERVADO;
    //private PacienteDto pacienteDto;
    private List<PrestacionDto> prestaciones;
}

