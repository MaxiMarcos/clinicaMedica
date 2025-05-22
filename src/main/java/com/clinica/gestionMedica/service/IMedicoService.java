package com.clinica.gestionMedica.service;

import com.clinica.gestionMedica.entity.Medico;
import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.enums.MedicoEstadoEnum;
import com.clinica.gestionMedica.enums.PrestacionTiposEnum;

import java.util.List;

public interface IMedicoService {

    Medico crearMedico(Medico medico);
    Medico editarMedico(Long id, Medico medico);
    Medico traerMedico(Long id);
    List<Medico> traerMedicos();
    void eliminarMedico(Long id);
    List<Medico> buscarPorEspecialidad(PrestacionTiposEnum especializacion);
    List<Medico> buscarPorEspecialidadYDisponibilidad(PrestacionTiposEnum especializacion, MedicoEstadoEnum disponibilidad);
    List<Medico> buscarPorApellido(String apellido);
}
