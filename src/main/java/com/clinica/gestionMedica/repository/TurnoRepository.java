package com.clinica.gestionMedica.repository;

import com.clinica.gestionMedica.entity.Turno;
import com.clinica.gestionMedica.enums.PresenciaEnum;
import com.clinica.gestionMedica.enums.PrestacionTiposEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {

    List<Turno> findByPrestacion_TipoAndEstado(PrestacionTiposEnum tipo, PresenciaEnum estado);
}