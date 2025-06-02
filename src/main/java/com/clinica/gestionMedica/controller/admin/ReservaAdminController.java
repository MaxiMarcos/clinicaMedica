package com.clinica.gestionMedica.controller.admin;

import com.clinica.gestionMedica.entity.Reserva;
import com.clinica.gestionMedica.service.impl.ReservaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/reserva")
public class ReservaAdminController {

    private final ReservaService reservaService;

    public ReservaAdminController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping("/traertodo")
    public ResponseEntity<?> traerReservas(){
        List<Reserva> reservas = reservaService.traerReservas();
        if(reservas != null){
            return ResponseEntity.status(HttpStatus.OK).body(reservas);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encontraron reservas");
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarReserva(Long id){
        Reserva reserva = reservaService.traerReserva(id);
        if (reserva != null){
            reservaService.eliminarReserva(reserva.getId());
            return ResponseEntity.status(HttpStatus.OK).body("Se eliminó exitosamente a la reserva con ID: "+ id);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encontró a la reserva con ID: "+ id);
        }
    }
}
