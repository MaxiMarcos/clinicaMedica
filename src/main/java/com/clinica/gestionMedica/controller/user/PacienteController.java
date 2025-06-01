package com.clinica.gestionMedica.controller.user;

import com.clinica.gestionMedica.dto.PacienteDto;
import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.service.impl.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    PacienteService pacienteService;


    @PostMapping("/crear")
    public ResponseEntity<?> crearPaciente(@RequestBody Paciente paciente){

        PacienteDto pacienteDto = pacienteService.crearPaciente(paciente);

        if(pacienteDto != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(pacienteDto);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear Paciente");
        }
    }

    @GetMapping("/historial/{id}") // probablemente deba modificar para poder usar #id == authentication.principal.id"
    public ResponseEntity<?> historialPaciente(@PathVariable Long id){

        PacienteDto historial = pacienteService.traerHistorial(id);

        if(historial != null){
            return ResponseEntity.status(HttpStatus.OK).body(historial);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al traer historial del Paciente con id: " + id);
        }
    }


    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarPaciente(@PathVariable Long id, @RequestBody Paciente paciente){

        PacienteDto pacienteEditado = pacienteService.editarPaciente(id, paciente);
        if(pacienteEditado != null){
            return ResponseEntity.status(HttpStatus.OK).body("Paciente editado exitosamente " + pacienteEditado);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encontró al paciente con ID: " + id);
        }
    }

}
