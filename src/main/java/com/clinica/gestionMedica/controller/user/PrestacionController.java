package com.clinica.gestionMedica.controller.user;

import com.clinica.gestionMedica.dto.PrestacionDto;
import com.clinica.gestionMedica.entity.Prestacion;
import com.clinica.gestionMedica.service.impl.PrestacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prestacion")
public class PrestacionController {

    @Autowired
    PrestacionService prestacionService;


    @GetMapping("traer/{id}")
    public ResponseEntity<?> traerPrestacionDto(@PathVariable Long id){

        PrestacionDto prestacionDto = prestacionService.traerPrestacionDto(id);

        if (prestacionDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prestación no encontrada con el ID: " + id);
        }

        return ResponseEntity.status(HttpStatus.OK).body(prestacionDto);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarPrestacion(Long id, Prestacion prestacion){
        Prestacion nuevaPrestacion = prestacionService.editarPrestacion(id, prestacion);
        if(nuevaPrestacion != null){
          return ResponseEntity.status(HttpStatus.OK).body("Prestación modificada correctamente " + nuevaPrestacion);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encontró la prestación con ID: " + id);
        }
    }

}
