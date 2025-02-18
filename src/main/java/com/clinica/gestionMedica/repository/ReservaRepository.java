package com.clinica.gestionMedica.repository;

import com.clinica.gestionMedica.entity.Reserva;
import com.clinica.gestionMedica.enums.ReservaEstadoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    Reserva findByPaciente_IdAndEstadoPago(Long id, ReservaEstadoEnum estadoPago);
}
