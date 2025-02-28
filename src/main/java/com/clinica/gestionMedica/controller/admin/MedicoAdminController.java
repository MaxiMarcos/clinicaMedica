package com.clinica.gestionMedica.controller.admin;

import com.clinica.gestionMedica.entity.Medico;
import com.clinica.gestionMedica.service.impl.MedicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medico-admin")
public class MedicoAdminController {

    private final MedicoService medicoService;

    public MedicoAdminController(MedicoService medicoService) {

        this.medicoService = medicoService;
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

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarMedico(@PathVariable Long id, @RequestBody Medico medico){
        Medico nuevoMedico = medicoService.editarMedico(id, medico);

        if(nuevoMedico != null){
            return ResponseEntity.status(HttpStatus.OK).body("Médico modificado exitosamente" + nuevoMedico);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encontró médico con el ID: "+ id);
        }
    }
}
