package com.clinica.gestionMedica.controller;

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

    @GetMapping("/traer")
    public ResponseEntity<?> traerReserva(@PathVariable Long id){
        Reserva reserva = reservaService.traerReserva(id);

        if(reserva != null){
            return ResponseEntity.status(HttpStatus.OK).body(reserva);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al traer la Reserva");
        }
    }

    @GetMapping("/traerTodo")
    public ResponseEntity<?> traerReservas(){
        List<Reserva> reservas = reservaService.traerReservas();
        if(reservas != null){
            return ResponseEntity.status(HttpStatus.OK).body("Se obtuvieron las siguientes reservas: "+ reservas);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encontraron reservas");
        }
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<?> eliminarReserva(Long id){
        Reserva reserva = reservaService.traerReserva(id);
        if (reserva != null){
            reservaService.eliminarReserva(reserva.getId());
            return ResponseEntity.status(HttpStatus.OK).body("Se eliminó exitosamente a la reserva con ID: "+ id);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encontró a la reserva con ID: "+ id);
        }
    }

    @PutMapping("/editar")
    public ResponseEntity<?> editarReserva(@PathVariable Long id,@RequestBody Reserva reserva){
        Reserva nuevaReserva = reservaService.editarReserva(id, reserva);
        if(nuevaReserva != null){
            return ResponseEntity.status(HttpStatus.OK).body("Reserva modificadda exitosamente "+ nuevaReserva);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encontró la reserva con ID: "+ id);
        }
    }
}
