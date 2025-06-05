package com.clinica.gestionMedica.controller.user;

import com.clinica.gestionMedica.dto.TurnoBusquedaRequestDto;
import com.clinica.gestionMedica.dto.TurnoRequestDto;
import com.clinica.gestionMedica.dto.TurnoResponseDto;
import com.clinica.gestionMedica.entity.Turno;
import com.clinica.gestionMedica.service.impl.TurnoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turno")
@RequiredArgsConstructor
public class TurnoController {

    private final TurnoService turnoService;

    @Operation(
            summary = "Adquirir turno",
            description = "Paciente elige un turno disponible y lo adquiere, vinculando su id con el turno."
    )
    @PutMapping("/pacientes/{pacienteId}/turnos/{turnoId}")
    public ResponseEntity<?> reservarTurno(@PathVariable Long pacienteId, @PathVariable Long turnoId) {

        TurnoResponseDto turnoResponseDto = turnoService.reservarTurno(pacienteId, turnoId);

        if(turnoResponseDto != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(turnoResponseDto);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear el turno");
        }
    }

    @GetMapping("/traer/{id}")
    public ResponseEntity<?> traerTurno(@PathVariable Long id){
        TurnoResponseDto turnoResponse = turnoService.traerTurno(id);

        return ResponseEntity.status(HttpStatus.OK).body(turnoResponse);
    }

    @GetMapping("/traer/filtro")
    public ResponseEntity<?> traerTurnosPorEspecialidadYDisponibilidad(@RequestBody TurnoBusquedaRequestDto requestDto){
        List<Turno> turnos = turnoService.buscarPorEspecialidadDisponibilidad(requestDto);

        if(turnos != null){
            return ResponseEntity.status(HttpStatus.OK).body(turnos);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al traer Turnos");
        }
    }
}
