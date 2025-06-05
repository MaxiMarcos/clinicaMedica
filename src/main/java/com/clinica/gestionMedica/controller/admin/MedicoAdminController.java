package com.clinica.gestionMedica.controller.admin;

import com.clinica.gestionMedica.dto.MedicoRequestDto;
import com.clinica.gestionMedica.dto.MedicoResponseDto;
import com.clinica.gestionMedica.entity.Medico;
import com.clinica.gestionMedica.enums.PrestacionTiposEnum;
import com.clinica.gestionMedica.service.impl.MedicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/medico")
@RequiredArgsConstructor
public class MedicoAdminController {

    private final MedicoService medicoService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearMedico(@Valid @RequestBody MedicoRequestDto medicoRequest){

        MedicoResponseDto medicoResponse = medicoService.crearMedico(medicoRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(medicoResponse);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarMedico(@PathVariable Long id, @RequestBody MedicoRequestDto medicoRequest){

        MedicoResponseDto medicoResponse = medicoService.editarMedico(id, medicoRequest);
        return ResponseEntity.status(HttpStatus.OK).body("Médico modificado exitosamente" + medicoResponse);
    }

    @GetMapping("/traer/{id}")
    public ResponseEntity<?> traerMedico(@PathVariable Long id){

        MedicoResponseDto medicoResponse = medicoService.traerMedico(id);
        if (medicoResponse != null){
            return ResponseEntity.status(HttpStatus.OK).body("Se obtuvo exitosamente el médico: " + medicoResponse);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al buscar a un médico con id "+ id);
        }
    }

    @GetMapping("/traertodo")
    public ResponseEntity<?> traerMedicos(){
        List<MedicoResponseDto> medicos = medicoService.traerMedicos();
        if (medicos != null){
            return ResponseEntity.status(HttpStatus.OK).body(medicos);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encontraron médicos ");
        }
    }

    @GetMapping("/traer/filtro-especialidad")
    public ResponseEntity<?> traerMedicosPorEspecialidad(PrestacionTiposEnum especialidad){
        List<MedicoResponseDto> medicos = medicoService.buscarPorEspecialidad(especialidad);
        if(medicos != null){
            return ResponseEntity.status(HttpStatus.OK).body(medicos);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falló la búsqueda de médicos con especialidad: "+ especialidad);
        }
    }

    @GetMapping("/traer/filtro-apellido")
    public ResponseEntity<?> traerMedicosPorApellido(String apellido){
        List<MedicoResponseDto> medicos = medicoService.buscarPorApellido(apellido);
        if(medicos != null){
            return ResponseEntity.status(HttpStatus.OK).body(medicos);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falló la búsqueda de médicos de apellido: "+ apellido);
        }
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarMedico(@PathVariable Long id){

            medicoService.eliminarMedico(id);
            return ResponseEntity.noContent().build();
    }
}
