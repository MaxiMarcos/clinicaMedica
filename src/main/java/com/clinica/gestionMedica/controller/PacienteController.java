package com.clinica.gestionMedica.controller;

import com.clinica.gestionMedica.dto.PacienteDto;
import com.clinica.gestionMedica.dto.ReservaPacienteDto;
import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.service.impl.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/traer")
    public ResponseEntity<?> traerPaciente(@PathVariable Long id){

        Paciente paciente = pacienteService.traerPaciente(id);
        if(paciente != null){
            return ResponseEntity.status(HttpStatus.OK).body(paciente);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al traer Paciente con id: " + id);
        }
    }

    @GetMapping("/historial/{dni}")
    public ResponseEntity<?> historialPaciente(@PathVariable String dni){

        PacienteDto historial = pacienteService.traerHistorial(dni);

        if(historial != null){
            return ResponseEntity.status(HttpStatus.OK).body(historial);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al traer historial del Paciente con dni: " + dni);
        }
    }

    @GetMapping("/traerTodo")
    public ResponseEntity<?> traerPacientes(){

        List<Paciente> pacientes = pacienteService.traerPacientes();
        if(pacientes != null){
            return ResponseEntity.status(HttpStatus.OK).body(pacientes);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al traer pacientes ");
        }
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Long id){

        Paciente paciente = pacienteService.traerPaciente(id);
        if (paciente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente no encontrado con el ID: " + id);
        }
        pacienteService.eliminarPaciente(paciente.getId());
        return ResponseEntity.status(HttpStatus.OK).body("Paciente eliminado correctamente con el ID: " + id);
    }

    @PutMapping("/editar")
    public ResponseEntity<?> editarPaciente(@PathVariable Long id, @RequestBody Paciente paciente){

        Paciente nuevoPaciente = pacienteService.editarPaciente(id, paciente);
        if(nuevoPaciente != null){
            return ResponseEntity.status(HttpStatus.OK).body("Paciente editado exitosamente " + nuevoPaciente);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encontró al paciente con ID: " + id);
        }
    }

}
