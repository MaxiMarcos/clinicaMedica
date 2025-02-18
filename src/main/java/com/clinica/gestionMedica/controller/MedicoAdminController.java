package com.clinica.gestionMedica.controller;

import com.clinica.gestionMedica.entity.Medico;
import com.clinica.gestionMedica.service.impl.MedicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medico-admin")
public class MedicoAdminController {

    private final MedicoService medicoService;

    public MedicoAdminController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @GetMapping("/traertodo")
    public ResponseEntity<?> traerMedicos(){
        List<Medico> medicos = medicoService.traerMedicos();
        if (medicos != null){
            return ResponseEntity.status(HttpStatus.OK).body(medicos);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encontraron médicos ");
        }
    }

    @DeleteMapping("/eliminar/{id}")
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
