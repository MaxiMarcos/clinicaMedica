package com.clinica.gestionMedica.repository;

import com.clinica.gestionMedica.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

   // List<Reserva> findByPaciente_Dni(String dni);
}
