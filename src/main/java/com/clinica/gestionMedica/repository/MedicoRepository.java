package com.clinica.gestionMedica.repository;

import com.clinica.gestionMedica.entity.Medico;
import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.enums.PrestacionTiposEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Medico findByNombre(String nombre);

    List<Medico> findByEspecializacion(PrestacionTiposEnum prestacionTipo);
}
