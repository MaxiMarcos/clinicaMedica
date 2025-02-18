package com.clinica.gestionMedica.dto;

import com.clinica.gestionMedica.entity.Medico;
import com.clinica.gestionMedica.entity.Reserva;
import com.clinica.gestionMedica.enums.PrestacionEstadoEnum;
import com.clinica.gestionMedica.enums.PrestacionTiposEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrestacionDto {

    private PrestacionTiposEnum tipo;
    private PrestacionEstadoEnum estado;
    private LocalDateTime fechaConsulta;
    private MedicoDto medicoDto;
}
