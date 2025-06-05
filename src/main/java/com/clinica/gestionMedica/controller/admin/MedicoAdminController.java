package com.clinica.gestionMedica.controller.admin;

import com.clinica.gestionMedica.dto.MedicoRequestDto;
import com.clinica.gestionMedica.dto.MedicoResponseDto;
import com.clinica.gestionMedica.enums.PrestacionTiposEnum;
import com.clinica.gestionMedica.service.impl.MedicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/medicos")
@RequiredArgsConstructor
public class MedicoAdminController {

    private final MedicoService medicoService;

    @PostMapping
    public ResponseEntity<?> crearMedico(@Valid @RequestBody MedicoRequestDto medicoRequest){

        MedicoResponseDto medicoResponse = medicoService.crearMedico(medicoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(medicoResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarMedico(@PathVariable Long id, @RequestBody MedicoRequestDto medicoRequest){

        MedicoResponseDto medicoResponse = medicoService.editarMedico(id, medicoRequest);
        return ResponseEntity.status(HttpStatus.OK).body(medicoResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> traerMedico(@PathVariable Long id){
        MedicoResponseDto medicoResponse = medicoService.traerMedico(id);
        return ResponseEntity.status(HttpStatus.OK).body(medicoResponse);
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<MedicoResponseDto>> traerMedicosFiltrados(
            @RequestParam(required = false) String especialidad,
            @RequestParam(required = false) String apellido) {

        List<MedicoResponseDto> medicos;

        if (especialidad != null) {
            PrestacionTiposEnum especialidadEnum = PrestacionTiposEnum.valueOf(especialidad.toUpperCase());
            medicos = medicoService.buscarPorEspecialidad(especialidadEnum);
        } else if (apellido != null) {
            medicos = medicoService.buscarPorApellido(apellido);
        } else {
            medicos = medicoService.traerMedicos();
        }
        return ResponseEntity.ok(medicos);
    }

    @GetMapping
    public ResponseEntity<List<MedicoResponseDto>> traerMedicos(){
        return ResponseEntity.status(HttpStatus.OK).body(medicoService.traerMedicos());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMedico(@PathVariable Long id){

        medicoService.eliminarMedico(id);
        return ResponseEntity.noContent().build();
    }
}
