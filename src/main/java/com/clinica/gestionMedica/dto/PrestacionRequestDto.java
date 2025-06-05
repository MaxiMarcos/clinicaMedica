package com.clinica.gestionMedica.dto;

import com.clinica.gestionMedica.enums.PrestacionTiposEnum;
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
    private PrestacionTiposEnum tipo;
    @NotBlank
    private String descripcion;
    @NotNull
    private Integer precio;
}
