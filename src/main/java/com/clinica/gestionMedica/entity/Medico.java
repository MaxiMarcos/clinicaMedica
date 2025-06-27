package com.clinica.gestionMedica.entity;

import com.clinica.gestionMedica.enums.TipoPrestacion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(referencedColumnName="id")
public class Medico extends Persona{

    @Enumerated(EnumType.STRING)
    private TipoPrestacion especializacion;
    private Double sueldo;

}
