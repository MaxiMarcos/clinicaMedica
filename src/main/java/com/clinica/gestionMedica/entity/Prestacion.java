package com.clinica.gestionMedica.entity;

import com.clinica.gestionMedica.enums.TipoPrestacion;
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
    private TipoPrestacion tipoPrestacion;

    private String descripcion;
    private Integer precio;
}
