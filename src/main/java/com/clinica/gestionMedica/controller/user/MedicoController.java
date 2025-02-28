package com.clinica.gestionMedica.controller.user;

import com.clinica.gestionMedica.entity.Medico;
import com.clinica.gestionMedica.enums.PrestacionTiposEnum;
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

    @GetMapping("/traertodo")
    public ResponseEntity<?> traerMedicos(){
        List<Medico> medicos = medicoService.traerMedicos();
        if (medicos != null){
            return ResponseEntity.status(HttpStatus.OK).body(medicos);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encontraron médicos ");
        }
    }

    @GetMapping("/traer/filtro-especialidad")
    public ResponseEntity<?> traerMedicosPorEspecialidad(PrestacionTiposEnum especialidad){
        List<Medico> medicos = medicoService.buscarPorEspecialidad(especialidad);
        if(medicos != null){
            return ResponseEntity.status(HttpStatus.OK).body(medicos);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falló la búsqueda de médicos con especialidad: "+ especialidad);
        }
    }

    @GetMapping("/traer/filtro-apellido")
    public ResponseEntity<?> traerMedicosPorApellido(String apellido){
        List<Medico> medicos = medicoService.buscarPorApellido(apellido);
        if(medicos != null){
            return ResponseEntity.status(HttpStatus.OK).body(medicos);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falló la búsqueda de médicos de apellido: "+ apellido);
        }
    }

}
