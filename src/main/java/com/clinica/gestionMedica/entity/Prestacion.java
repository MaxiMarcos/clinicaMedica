package com.clinica.gestionMedica.entity;

import com.clinica.gestionMedica.enums.PrestacionTiposEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Prestacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PrestacionTiposEnum tipo;

    private String descripcion;
    private Integer precio;
}
