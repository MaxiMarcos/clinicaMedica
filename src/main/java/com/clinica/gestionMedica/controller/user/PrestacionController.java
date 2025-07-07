package com.clinica.gestionMedica.controller.user;

import com.clinica.gestionMedica.dto.PrestacionRequestDto;
import com.clinica.gestionMedica.dto.PrestacionResponseDto;
import com.clinica.gestionMedica.service.impl.PrestacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prestaciones")
@RequiredArgsConstructor
public class PrestacionController {

    private final PrestacionService prestacionService;

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

}
