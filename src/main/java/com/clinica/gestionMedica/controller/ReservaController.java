package com.clinica.gestionMedica.controller;

import com.clinica.gestionMedica.dto.ReservaDto;
import com.clinica.gestionMedica.entity.Prestacion;
import com.clinica.gestionMedica.entity.Reserva;
import com.clinica.gestionMedica.service.impl.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reserva")
public class ReservaController {

    @Autowired
    ReservaService reservaService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearReserva(@RequestBody Reserva reserva){

        Reserva reserva2 = reservaService.crearReserva(reserva);
        if(reserva2 != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(reserva2);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear la Reserva");
        }
    }

    // proceso de obtener/comprar turno
    @PostMapping("/{pacienteId}/{prestacionId}")
    public ResponseEntity<?> agregarPrestacionEnReserva( @PathVariable Long pacienteId,
                                                         @PathVariable Long prestacionId){

        ReservaDto reservaDto = reservaService.agregarPrestacionEnReserva(pacienteId, prestacionId);
        if(reservaDto != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(reservaDto);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear la Reserva");
        }
    }

    @GetMapping("/traer/{id}")
    public ResponseEntity<?> traerReserva(@PathVariable Long id){
        Reserva reserva = reservaService.traerReserva(id);

        if(reserva != null){
            return ResponseEntity.status(HttpStatus.OK).body(reserva);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al traer la Reserva");
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarReserva(@PathVariable Long id,@RequestBody Reserva reserva){
        Reserva nuevaReserva = reservaService.editarReserva(id, reserva);
        if(nuevaReserva != null){
            return ResponseEntity.status(HttpStatus.OK).body("Reserva modificadda exitosamente "+ nuevaReserva);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encontró la reserva con ID: "+ id);
        }
    }
}
