package com.clinica.gestionMedica.controller.user;

import com.clinica.gestionMedica.dto.PrestacionRequestDto;
import com.clinica.gestionMedica.dto.PrestacionResponseDto;
import com.clinica.gestionMedica.entity.Prestacion;
import com.clinica.gestionMedica.service.impl.PrestacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prestacion")
@RequiredArgsConstructor
public class PrestacionController {

    private final PrestacionService prestacionService;


    @GetMapping("traer/{id}")
    public ResponseEntity<?> traerPrestacionResponseDto(@PathVariable Long id){

        PrestacionResponseDto PrestacionResponseDto = prestacionService.traerPrestacion(id);
        return ResponseEntity.status(HttpStatus.OK).body(PrestacionResponseDto);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarPrestacion(Long id, PrestacionRequestDto prestacionRequest){
        PrestacionResponseDto prestacionResponse = prestacionService.editarPrestacion(id, prestacionRequest);
        return ResponseEntity.status(HttpStatus.OK).body("Prestación modificada correctamente " + prestacionResponse);

    }

}
