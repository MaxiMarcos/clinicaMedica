package com.clinica.gestionMedica.dto;

import com.clinica.gestionMedica.enums.TipoPrestacion;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrestacionRequestDto {

    @NotNull
    private TipoPrestacion tipoPrestacion;
    @NotBlank
    private String descripcion;
    @NotNull
    private Integer precio;
}
