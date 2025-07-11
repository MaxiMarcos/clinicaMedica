package com.clinica.gestionMedica.controller.user;

import com.clinica.gestionMedica.dto.TurnoBusquedaRequestDto;
import com.clinica.gestionMedica.dto.TurnoResponseDto;
import com.clinica.gestionMedica.service.impl.TurnoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
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
        return ResponseEntity.status(HttpStatus.CREATED).body(turnoResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> traerTurno(@PathVariable Long id){
        TurnoResponseDto turnoResponse = turnoService.traerTurno(id);
        return ResponseEntity.status(HttpStatus.OK).body(turnoResponse);
    }

    @PostMapping("/filtro")
    public ResponseEntity<?> traerPorMedicos(@RequestBody TurnoBusquedaRequestDto requestDto){
        List<TurnoResponseDto> turnos = turnoService.buscarPorMedico(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(turnos);
    }

    @PutMapping("/pacientes/{pacienteId}/cancelacion/{turnoId}")
    public ResponseEntity<?> cancelarTurno(@PathVariable Long pacienteId, @PathVariable Long turnoId){
        TurnoResponseDto turnoResponse = turnoService.cancelarTurno(pacienteId, turnoId);
        return ResponseEntity.status(HttpStatus.OK).body(turnoResponse);
    }
}
