package com.clinica.gestionMedica.service;


import com.clinica.gestionMedica.dto.TurnoRequestDto;
import com.clinica.gestionMedica.dto.TurnoResponseDto;
import com.clinica.gestionMedica.entity.Turno;

import java.util.List;

public interface ITurnoService{

    TurnoResponseDto crearTurno(TurnoRequestDto turnoRequest);
    TurnoResponseDto reservarTurno(Long pacienteId, Long turnoId);
    TurnoResponseDto editarTurno(Long id, TurnoRequestDto turnoRequest);
    TurnoResponseDto traerTurno(Long id);
    List<TurnoResponseDto> traerTurnos();
    TurnoResponseDto cancelarTurno(Long pacienteId, Long turnoId);
    void eliminarTurno(Long id);

}
