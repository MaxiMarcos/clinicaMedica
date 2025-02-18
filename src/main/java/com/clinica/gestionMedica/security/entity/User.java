package com.clinica.gestionMedica.security.entity;

import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.entity.Persona;
import com.clinica.gestionMedica.security.enumRole.RoleName;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleName role = RoleName.CUSTOMER;

    // deberia cambiarse a onetoone probablemente
    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

}
