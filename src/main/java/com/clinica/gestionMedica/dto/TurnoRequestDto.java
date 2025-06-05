package com.clinica.gestionMedica.dto;

import com.clinica.gestionMedica.entity.Medico;
import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.entity.Prestacion;
import com.clinica.gestionMedica.enums.PresenciaEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TurnoRequestDto {

    @NotBlank
    private int codigoTurno;
    @NotNull
    private LocalDateTime fechaConsulta;
    @NotNull
    private PresenciaEnum estado = PresenciaEnum.DISPONIBLE;
    private Paciente paciente;
    @NotNull
    private Prestacion prestacion;
    @NotNull
    private Medico medico;
}
