package com.clinica.gestionMedica.controller.user;

import com.clinica.gestionMedica.dto.PrestacionRequestDTO;
import com.clinica.gestionMedica.dto.ReservaDto;
import com.clinica.gestionMedica.entity.Reserva;
import com.clinica.gestionMedica.service.impl.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // se genera reserva con prestaciones
    @PostMapping("/obtener-turno")
    public ResponseEntity<?> agregarPrestacionEnReserva(@RequestBody PrestacionRequestDTO prestacionRequestDTO){

        ReservaDto reservaDto = reservaService.agregarPrestacionEnReserva(prestacionRequestDTO);

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
