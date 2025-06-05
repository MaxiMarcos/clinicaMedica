package com.clinica.gestionMedica.controller.admin;

import com.clinica.gestionMedica.dto.PrestacionRequestDto;
import com.clinica.gestionMedica.dto.PrestacionResponseDto;
import com.clinica.gestionMedica.entity.Prestacion;
import com.clinica.gestionMedica.service.impl.PrestacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/prestacion")
@RequiredArgsConstructor
public class PrestacionAdminController {

    private final PrestacionService prestacionService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearPrestacionAdmin(@Valid @RequestBody PrestacionRequestDto prestacionRequest) {

        PrestacionResponseDto prestacionResponse = prestacionService.crearPrestacion(prestacionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(prestacionResponse);
    }

    @GetMapping("traertodo")
    public ResponseEntity<?> traerPrestaciones(){

        List<PrestacionResponseDto> prestacionesResponse = prestacionService.traerPrestaciones();

        if (prestacionesResponse!= null) {
            return ResponseEntity.status(HttpStatus.OK).body(prestacionesResponse);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al traer prestaciones");
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPrestacion(@PathVariable Long id) {
        prestacionService.eliminarPrestacion(id);
        return ResponseEntity.noContent().build();
    }
}
