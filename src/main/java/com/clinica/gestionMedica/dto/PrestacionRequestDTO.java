package com.clinica.gestionMedica.dto;

import com.clinica.gestionMedica.enums.PrestacionTiposEnum;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrestacionRequestDTO {

    private Long pacienteId;
    private PrestacionTiposEnum tipo;
    private List<Long> prestacionId;
}
