package com.clinica.gestionMedica.controller.user;

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

    @PostMapping("/obtener-turno")
    public ResponseEntity<?> crearPrestacionCliente(@RequestBody Prestacion prestacion) {

        Prestacion nuevaPrestacion = prestacionService.crearPrestacion(prestacion);

        if (nuevaPrestacion != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPrestacion);
        } else {
            return ResponseEntity.badRequest().body("Error al crear la prestación. Verifique los datos enviados.");
        }
    }

    @GetMapping("traer/{id}")
    public ResponseEntity<String> traerPrestacion(@PathVariable Long id){

        Prestacion prestacion = prestacionService.traerPrestacion(id);

        if (prestacion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prestación no encontrada con el ID: " + id);
        }

        return ResponseEntity.status(HttpStatus.OK).body("Prestación obtenida correctamente: " + prestacion);
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
