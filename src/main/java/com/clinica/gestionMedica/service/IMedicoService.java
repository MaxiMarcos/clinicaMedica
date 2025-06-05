package com.clinica.gestionMedica.service;

import com.clinica.gestionMedica.dto.MedicoRequestDto;
import com.clinica.gestionMedica.dto.MedicoResponseDto;
import com.clinica.gestionMedica.enums.PrestacionTiposEnum;

import java.util.List;

public interface IMedicoService {

    MedicoResponseDto crearMedico(MedicoRequestDto medico);
    MedicoResponseDto editarMedico(Long id, MedicoRequestDto medicoRequest);
    MedicoResponseDto traerMedico(Long id);
    List<MedicoResponseDto> traerMedicos();
    void eliminarMedico(Long id);
    List<MedicoResponseDto> buscarPorEspecialidad(PrestacionTiposEnum especializacion);
    List<MedicoResponseDto> buscarPorApellido(String apellido);
}
