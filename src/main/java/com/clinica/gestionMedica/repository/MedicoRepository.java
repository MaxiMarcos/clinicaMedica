package com.clinica.gestionMedica.repository;

import com.clinica.gestionMedica.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
}
