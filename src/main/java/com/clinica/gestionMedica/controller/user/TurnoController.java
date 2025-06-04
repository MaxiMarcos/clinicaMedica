package com.clinica.gestionMedica.controller.user;

import com.clinica.gestionMedica.dto.PrestacionRequestDTO;
import com.clinica.gestionMedica.dto.TurnoDto;
import com.clinica.gestionMedica.entity.Turno;
import com.clinica.gestionMedica.service.impl.TurnoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turno")
public class TurnoController {

    @Autowired
    TurnoService turnoService;

    @Operation(
            summary = "Adquirir turno",
            description = "Paciente elige un turno disponible y lo adquiere, vinculando su id con el turno."
    )
    @PutMapping("/pacientes/{pacienteId}/turnos/{turnoId}")
    public ResponseEntity<?> agregarPrestacionEnTurno(@PathVariable Long pacienteId,
                                                        @PathVariable Long turnoId) {

        TurnoDto turnoDto = turnoService.agregarPrestacionEnTurno(pacienteId, turnoId);

        if(turnoDto != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(turnoDto);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear el turno");
        }
    }

    @GetMapping("/traer/{id}")
    public ResponseEntity<?> traerTurno(@PathVariable Long id){
        Turno turno = turnoService.traerTurno(id);

        if(turno != null){
            return ResponseEntity.status(HttpStatus.OK).body(turno);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al traer la Turno");
        }
    }

    @GetMapping("/traer/filtro")
    public ResponseEntity<?> traerTurnosPorEspecialidadYDisponibilidad(@RequestBody PrestacionRequestDTO prestacionRequestDTO){
        List<Turno> Turnos = turnoService.buscarPorEspecialidadDisponibilidad(prestacionRequestDTO);

        if(Turnos != null){
            return ResponseEntity.status(HttpStatus.OK).body(Turnos);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al traer Turnos");
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarTurno(@PathVariable Long id,@RequestBody Turno turno){
        Turno nuevaTurno = turnoService.editarTurno(id, turno);
        if(nuevaTurno != null){
            return ResponseEntity.status(HttpStatus.OK).body("Turno modificadda exitosamente "+ nuevaTurno);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encontró la Turno con ID: "+ id);
        }
    }
}
