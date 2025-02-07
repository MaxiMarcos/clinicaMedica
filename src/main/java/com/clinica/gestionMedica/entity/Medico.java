package com.clinica.gestionMedica.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(referencedColumnName="id")
public class Medico extends Persona{

    private String especialidadMedica;
    //private Map<LocalDate, List<LocalTime>> turnosDisponibles;
    private Double sueldo;

    @OneToMany(mappedBy = "medico")
    private List<Reserva> listaReservas;
}
