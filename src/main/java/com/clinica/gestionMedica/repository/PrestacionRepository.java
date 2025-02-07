package com.clinica.gestionMedica.repository;

import com.clinica.gestionMedica.entity.Prestacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestacionRepository extends JpaRepository<Prestacion, Long> {
}
