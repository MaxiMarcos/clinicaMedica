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
@RequestMapping("/admin/prestaciones")
@RequiredArgsConstructor
public class PrestacionAdminController {

    private final PrestacionService prestacionService;

    @PostMapping
    public ResponseEntity<?> crearPrestacionAdmin(@Valid @RequestBody PrestacionRequestDto prestacionRequest) {

        PrestacionResponseDto prestacionResponse = prestacionService.crearPrestacion(prestacionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(prestacionResponse);
    }

    @GetMapping
    public ResponseEntity<?> traerPrestaciones(){

        List<PrestacionResponseDto> prestacionesResponse = prestacionService.traerPrestaciones();
        return ResponseEntity.status(HttpStatus.OK).body(prestacionesResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> traerPrestacion(@PathVariable Long id){

        PrestacionResponseDto PrestacionResponseDto = prestacionService.traerPrestacion(id);
        return ResponseEntity.status(HttpStatus.OK).body(PrestacionResponseDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editarPrestacion(@PathVariable Long id, @RequestBody PrestacionRequestDto prestacionRequest){
        PrestacionResponseDto prestacionResponse = prestacionService.editarPrestacion(id, prestacionRequest);
        return ResponseEntity.status(HttpStatus.OK).body("Prestación modificada correctamente " + prestacionResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> eliminarPrestacion(@PathVariable Long id) {
        prestacionService.eliminarPrestacion(id);
        return ResponseEntity.noContent().build();
    }
}
