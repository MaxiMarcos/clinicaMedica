package com.clinica.gestionMedica.entity;

import com.clinica.gestionMedica.enums.PresenciaEnum;
import com.clinica.gestionMedica.enums.ReservaEnum;
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
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double precioTotal;

    @Enumerated(EnumType.STRING)
    private ReservaEnum estadoPago;
    @Enumerated(EnumType.STRING)
    private PresenciaEnum estadoPresencia;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;
}
