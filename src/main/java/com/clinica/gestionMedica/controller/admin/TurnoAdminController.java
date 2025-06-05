package com.clinica.gestionMedica.controller.admin;

import com.clinica.gestionMedica.dto.TurnoRequestDto;
import com.clinica.gestionMedica.dto.TurnoResponseDto;
import com.clinica.gestionMedica.entity.Turno;
import com.clinica.gestionMedica.service.impl.TurnoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/turnos")
@RequiredArgsConstructor
public class TurnoAdminController {

    private final TurnoService turnoService;

    @PostMapping
    public ResponseEntity<?> crearTurno(@Valid @RequestBody TurnoRequestDto turnoRequest){

        TurnoResponseDto turno = turnoService.crearTurno(turnoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(turno);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editarTurno(@PathVariable Long id,@RequestBody TurnoRequestDto turnoRequestDto){

        TurnoResponseDto turnoResponse = turnoService.editarTurno(id, turnoRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body("Turno modificadda exitosamente "+ turnoResponse);
    }

    @GetMapping
    public ResponseEntity<?> traerTurnos(){
        List<TurnoResponseDto> turnos = turnoService.traerTurnos();
        return ResponseEntity.status(HttpStatus.OK).body(turnos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> traerTurno(@PathVariable Long id){
        TurnoResponseDto turnoResponse = turnoService.traerTurno(id);
        return ResponseEntity.status(HttpStatus.OK).body(turnoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTurno(Long id){
        turnoService.eliminarTurno(id);
        return ResponseEntity.status(HttpStatus.OK).body("Se eliminó exitosamente el turno con ID: "+ id);
    }
}
