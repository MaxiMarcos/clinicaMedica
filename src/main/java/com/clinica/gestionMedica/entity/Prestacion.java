package com.clinica.gestionMedica.entity;

import com.clinica.gestionMedica.enums.PresenciaEnum;
import com.clinica.gestionMedica.enums.PrestacionEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    @Enumerated(EnumType.STRING)
    private PresenciaEnum estado = PresenciaEnum.DISPONIBLE;
    private int codigoPrestacion;
    private String descripcion;
    private double precio;
    private LocalDateTime fechaConsulta;

    @ManyToOne
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;
}
