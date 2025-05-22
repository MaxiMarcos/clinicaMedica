package com.clinica.gestionMedica.entity;

import com.clinica.gestionMedica.dto.MedicoDto;
import com.clinica.gestionMedica.enums.PrestacionTiposEnum;
import com.clinica.gestionMedica.enums.PrestacionEstadoEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
    @Enumerated(EnumType.STRING)
    private PrestacionEstadoEnum estado = PrestacionEstadoEnum.DISPONIBLE;
    private int codigoPrestacion;
    private String descripcion;
    private double precio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaConsulta;

    @ManyToOne
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medico;
}
