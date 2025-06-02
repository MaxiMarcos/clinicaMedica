package com.clinica.gestionMedica.repository;

import com.clinica.gestionMedica.dto.PrestacionDto;
import com.clinica.gestionMedica.entity.Medico;
import com.clinica.gestionMedica.entity.Prestacion;
import com.clinica.gestionMedica.enums.PrestacionEstadoEnum;
import com.clinica.gestionMedica.enums.PrestacionTiposEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PrestacionRepository extends JpaRepository<Prestacion, Long> {

}
