package com.clinica.gestionMedica.dto;

import com.clinica.gestionMedica.enums.PrestacionTiposEnum;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrestacionResponseDto {

    private Long id;
    private String descripcion;
    private int precio;
    private PrestacionTiposEnum tipo;
}
