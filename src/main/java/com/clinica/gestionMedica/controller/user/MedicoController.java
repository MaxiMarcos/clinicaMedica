package com.clinica.gestionMedica.controller.user;

import com.clinica.gestionMedica.dto.MedicoResponseDto;
import com.clinica.gestionMedica.service.impl.MedicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/medicos")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoService medicoService;

    @GetMapping
    public ResponseEntity<List<MedicoResponseDto>> traerMedicos(){
        return ResponseEntity.status(HttpStatus.OK).body(medicoService.traerMedicos());
    }
}
