package com.clinica.gestionMedica.controller;

import com.clinica.gestionMedica.entity.Prestacion;
import com.clinica.gestionMedica.service.impl.PrestacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prestacion")
public class PrestacionController {

    @Autowired
    PrestacionService prestacionService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearPrestacion(@RequestBody Prestacion prestacion) {
        Prestacion nuevaPrestacion = prestacionService.crearPrestacion(prestacion);

        if (nuevaPrestacion != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPrestacion);
        } else {
            return ResponseEntity.badRequest().body("Error al crear la prestación. Verifique los datos enviados.");
        }
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarPrestacion(@PathVariable Long id) {
        Prestacion prestacion = prestacionService.traerPrestacion(id);

        if (prestacion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prestación no encontrada con el ID: " + id);
        }

        prestacionService.eliminarPrestacion(prestacion.getId());
        return ResponseEntity.status(HttpStatus.OK).body("Prestación eliminada correctamente con el ID: " + id);
    }

    @GetMapping("traer")
    public ResponseEntity<String> traerPrestacion(@PathVariable Long id){

        Prestacion prestacion = prestacionService.traerPrestacion(id);

        if (prestacion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prestación no encontrada con el ID: " + id);
        }

        return ResponseEntity.status(HttpStatus.OK).body("Prestación obtenida correctamente: " + prestacion);
    }

    @GetMapping("traerTodo")
    public ResponseEntity<String> traerPrestaciones(){

        List<Prestacion> prestaciones = prestacionService.traerPrestaciones();

        if (prestaciones == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prestaciónes no encontradas");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Prestaciónes obtenidas correctamente: " + prestaciones);
    }

    @PutMapping("/editar")
    public ResponseEntity<?> editarPrestacion(Long id, Prestacion prestacion){
        Prestacion nuevaPrestacion = prestacionService.editarPrestacion(id, prestacion);
        if(nuevaPrestacion != null){
          return ResponseEntity.status(HttpStatus.OK).body("Prestación modificada correctamente " + nuevaPrestacion);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encontró la prestación con ID: " + id);
        }
    }

}
