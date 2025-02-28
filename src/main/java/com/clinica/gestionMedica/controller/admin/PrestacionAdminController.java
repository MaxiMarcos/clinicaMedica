package com.clinica.gestionMedica.controller.admin;

import com.clinica.gestionMedica.entity.Prestacion;
import com.clinica.gestionMedica.service.impl.PrestacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prestacion-admin")
public class PrestacionAdminController {

    private final PrestacionService prestacionService;

    public PrestacionAdminController(PrestacionService prestacionService) {
        this.prestacionService = prestacionService;
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearPrestacionAdmin(@RequestBody Prestacion prestacion) {

        Prestacion nuevaPrestacion = prestacionService.crearPrestacionAdmin(prestacion);

        if (nuevaPrestacion != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPrestacion);
        } else {
            return ResponseEntity.badRequest().body("Error al crear la prestación. Verifique los datos enviados.");
        }
    }

    @GetMapping("traertodo")
    public ResponseEntity<?> traerPrestaciones(){

        List<Prestacion> prestaciones = prestacionService.traerPrestaciones();

        if (prestaciones!= null) {
            return ResponseEntity.status(HttpStatus.OK).body(prestaciones);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al traer prestaciones");
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPrestacion(@PathVariable Long id) {
        Prestacion prestacion = prestacionService.traerPrestacion(id);

        if (prestacion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prestación no encontrada con el ID: " + id);
        }

        prestacionService.eliminarPrestacion(prestacion.getId());
        return ResponseEntity.status(HttpStatus.OK).body("Prestación eliminada correctamente con el ID: " + id);
    }
}
