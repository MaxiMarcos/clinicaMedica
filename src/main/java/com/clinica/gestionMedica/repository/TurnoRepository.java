package com.clinica.gestionMedica.repository;

import com.clinica.gestionMedica.entity.Turno;
import com.clinica.gestionMedica.enums.PresenciaEnum;
import com.clinica.gestionMedica.enums.TipoPrestacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {

    List<Turno> findByPrestacion_TipoPrestacionAndEstado(TipoPrestacion tipoPrestacion, PresenciaEnum estado);

    @Query("SELECT MAX(t.codigoTurno) FROM Turno t")
    Optional<Integer> findMaxCodigoTurno();
}