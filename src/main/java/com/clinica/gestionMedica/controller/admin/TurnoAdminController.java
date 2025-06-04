package com.clinica.gestionMedica.controller.admin;

import com.clinica.gestionMedica.entity.Turno;
import com.clinica.gestionMedica.service.impl.TurnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/turno")
public class TurnoAdminController {

    private final TurnoService turnoService;

    public TurnoAdminController(TurnoService turnoService) {

        this.turnoService = turnoService;
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearTurno(@RequestBody Turno turno){

        Turno turno2 = turnoService.crearTurno(turno);
        if(turno2 != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(turno2);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear el turno");
        }
    }

    @GetMapping("/traertodo")
    public ResponseEntity<?> traerTurno(){
        List<Turno> turnos = turnoService.traerTurnos();
        if(turnos != null){
            return ResponseEntity.status(HttpStatus.OK).body(turnos);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encontraron turnos");
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarTurno(Long id){
        Turno turno = turnoService.traerTurno(id);
        if (turno != null){
            turnoService.eliminarTurno(turno.getId());
            return ResponseEntity.status(HttpStatus.OK).body("Se eliminó exitosamente el turno con ID: "+ id);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encontró el turno con ID: "+ id);
        }
    }
}
