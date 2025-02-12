package com.clinica.gestionMedica.repository;

import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {


    Paciente findByEmail(String email);
}
