package com.clinica.gestionMedica.controller;

import com.clinica.gestionMedica.dto.PacienteDto;
import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.service.impl.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    PacienteService pacienteService;


    @PostMapping("/crear")
    public ResponseEntity<?> crearPaciente(@RequestBody Paciente paciente){

        Paciente pacienteCreado = pacienteService.crearPaciente(paciente);

        if(pacienteCreado != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(pacienteCreado);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear Paciente");
        }
    }

    @GetMapping("/traer/{id}")
    public ResponseEntity<?> traerPaciente(@PathVariable Long id){

        Paciente paciente = pacienteService.traerPaciente(id);
        if(paciente != null){
            return ResponseEntity.status(HttpStatus.OK).body(paciente);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al traer Paciente con id: " + id);
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

        Paciente nuevoPaciente = pacienteService.editarPaciente(id, paciente);
        if(nuevoPaciente != null){
            return ResponseEntity.status(HttpStatus.OK).body("Paciente editado exitosamente " + nuevoPaciente);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encontró al paciente con ID: " + id);
        }
    }

}
