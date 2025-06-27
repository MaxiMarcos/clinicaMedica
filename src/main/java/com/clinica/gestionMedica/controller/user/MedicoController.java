package com.clinica.gestionMedica.controller.user;

import com.clinica.gestionMedica.dto.MedicoResponseDto;
import com.clinica.gestionMedica.enums.TipoPrestacion;
import com.clinica.gestionMedica.service.impl.MedicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoService medicoService;


    @GetMapping("/{id}")
    public ResponseEntity<?> traerMedico(@PathVariable Long id){
        MedicoResponseDto medicoResponse = medicoService.traerMedico(id);
        return ResponseEntity.status(HttpStatus.OK).body(medicoResponse);
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<MedicoResponseDto>> traerMedicosFiltrados(
            @RequestParam(required = false) String especialidad,
            @RequestParam(required = false) String apellido) {

        List<MedicoResponseDto> medicos = medicoService.filtrarMedicos(apellido, especialidad);

        return ResponseEntity.ok(medicos);
    }

    @GetMapping
    public ResponseEntity<List<MedicoResponseDto>> traerMedicos(){
        return ResponseEntity.status(HttpStatus.OK).body(medicoService.traerMedicos());
    }

}
