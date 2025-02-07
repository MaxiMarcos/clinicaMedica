package com.clinica.gestionMedica.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(referencedColumnName="id")
public class Paciente extends Persona {

    @OneToMany(mappedBy = "paciente")
    private List<Reserva> listaReserva;
}
