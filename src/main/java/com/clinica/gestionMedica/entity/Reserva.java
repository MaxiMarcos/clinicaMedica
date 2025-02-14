package com.clinica.gestionMedica.entity;

import com.clinica.gestionMedica.enums.PresenciaEnum;
import com.clinica.gestionMedica.enums.ReservaEstadoEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double precioTotal = 0.0;

    @Enumerated(EnumType.STRING)
    private ReservaEstadoEnum estadoPago = ReservaEstadoEnum.PENDIENTE;
    @Enumerated(EnumType.STRING)
    private PresenciaEnum estadoPresencia = PresenciaEnum.RESERVADO;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    //@ManyToOne
   // @JoinColumn(name = "medico_id") Médico ya está asociado en Prestación, la cual se relaciona con Reserva
    //private Medico medico;

    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Prestacion> prestaciones;
}
