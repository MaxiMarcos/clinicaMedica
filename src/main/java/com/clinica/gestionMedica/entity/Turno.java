package com.clinica.gestionMedica.entity;

import com.clinica.gestionMedica.enums.PresenciaEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private int codigoTurno;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaConsulta;
    @Enumerated(EnumType.STRING)
    private PresenciaEnum estado = PresenciaEnum.DISPONIBLE;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable =  true)
    private Paciente paciente;
    @ManyToOne
    @JoinColumn(name = "prestacion_id")
    private Prestacion prestacion;
    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medico;
}
