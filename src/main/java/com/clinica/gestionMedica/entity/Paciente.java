package com.clinica.gestionMedica.entity;

import com.clinica.gestionMedica.enums.ObraSocialEnum;
import com.clinica.gestionMedica.security.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(referencedColumnName="id")
public class Paciente extends Persona {

    @Enumerated(EnumType.STRING)
    private ObraSocialEnum obraSocial;
    @OneToMany(mappedBy = "paciente")
    private List<Turno> listaTurnos;

    @ManyToMany(mappedBy = "pacientes")
    private Set<Clinica> clinicas = new HashSet<>();
}
