package com.clinica.gestionMedica.dto;

import com.clinica.gestionMedica.enums.TipoPrestacion;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrestacionResponseDto {

    private Long id;
    private String descripcion;
    private Integer precio;
    private TipoPrestacion tipo;
}
