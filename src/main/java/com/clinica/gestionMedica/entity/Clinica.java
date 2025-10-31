package com.clinica.gestionMedica.entity;

import com.clinica.gestionMedica.enums.TipoPrestacion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Clinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;


    @ManyToMany
    @JoinTable(
            name = "clinica_paciente",
            joinColumns = @JoinColumn(name = "clinica_id"),
            inverseJoinColumns = @JoinColumn(name = "pacientes_id")
    )
    private Set<Paciente> pacientes = new HashSet<>();
    @OneToMany(mappedBy = "clinica")
    private List<Medico> medicos = new ArrayList<>();
}