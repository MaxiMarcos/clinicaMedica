package com.clinica.gestionMedica.entity;

import com.clinica.gestionMedica.enums.PrestacionEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Prestacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PrestacionEnum tipo;
    private int codigoPrestacion;
    private String descripcion;
    private double precio;
    private LocalDateTime fechaConsulta;
}
