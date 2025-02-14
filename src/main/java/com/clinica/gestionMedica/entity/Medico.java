package com.clinica.gestionMedica.entity;

import com.clinica.gestionMedica.enums.PrestacionTiposEnum;
import jakarta.persistence.*;
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

    //private Map<LocalDate, List<LocalTime>> turnosDisponibles;
    @Enumerated(EnumType.STRING)
    private PrestacionTiposEnum especializacion;
    private Double sueldo;


   // @OneToMany(mappedBy = "medico")
   // private List<Reserva> listaReservas;
}
