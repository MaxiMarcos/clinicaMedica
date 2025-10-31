package com.clinica.gestionMedica.repository;

import com.clinica.gestionMedica.entity.Clinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicaRepository extends JpaRepository<Clinica, Long> {
}