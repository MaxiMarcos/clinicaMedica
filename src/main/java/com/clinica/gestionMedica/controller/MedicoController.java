package com.clinica.gestionMedica.controller;

import com.clinica.gestionMedica.entity.Medico;
import com.clinica.gestionMedica.entity.Reserva;
import com.clinica.gestionMedica.service.impl.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medico")
public class MedicoController {

    @Autowired
    MedicoService medicoService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearMedico(@RequestBody Medico medico){

        Medico medico2 = medicoService.crearMedico(medico);

        if (medico2 != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(medico2);
        } else {
            return ResponseEntity.badRequest().body("Error al crear el perfil médico. Verifique los datos enviados.");
        }
    }

    @GetMapping("/traer/{id}")
    public ResponseEntity<?> traerMedico(@PathVariable Long id){

        Medico medico = medicoService.traerMedico(id);
        if (medico != null){
            return ResponseEntity.status(HttpStatus.OK).body("Se obtuvo exitosamente el médico: " + medico);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al buscar a un médico con id "+ id);
        }
    }

    @GetMapping("/traerTodo")
    public ResponseEntity<?> traerMedicos(){
        List<Medico> medicos = medicoService.traerMedicos();
        if (medicos != null){
            return ResponseEntity.status(HttpStatus.OK).body("Se obtuvieron exitosamente los siguientes médicos: " + medicos);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encontraron médicos ");
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarMedico(@PathVariable Long id, @RequestBody Medico medico){
        Medico nuevoMedico = medicoService.editarMedico(id, medico);

        if(nuevoMedico != null){
            return ResponseEntity.status(HttpStatus.OK).body("Médico modificado exitosamente" + nuevoMedico);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encontró médico con el ID: "+ id);
        }
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<?> eliminarMedico(@PathVariable Long id){

        Medico medico = medicoService.traerMedico(id);
        if(medico != null){
            medicoService.eliminarMedico(medico.getId());
            return ResponseEntity.status(HttpStatus.OK).body("Se eliminó correctamente al médico con ID: "+ id);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encontró al médico con ID: "+ id);
        }
    }
}
