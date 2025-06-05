package com.clinica.gestionMedica.controller.admin;

import com.clinica.gestionMedica.dto.PacienteRequestDto;
import com.clinica.gestionMedica.dto.PacienteResponseDto;
import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.mapper.PacienteMapper;
import com.clinica.gestionMedica.repository.PacienteRepository;
import com.clinica.gestionMedica.service.impl.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/paciente")
@RequiredArgsConstructor
public class PacienteAdminController {

    private final PacienteService pacienteService;
    private final PacienteMapper pacienteMapper;

    @PostMapping("/crear")
    public ResponseEntity<?> crearPaciente(@RequestBody PacienteRequestDto pacienteRequest){

        PacienteResponseDto pacienteResponseDto = pacienteService.crearPaciente(pacienteRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteResponseDto);
    }

    @GetMapping("/traer-todo")
    public ResponseEntity<?> traerPacientes(){

        List<PacienteResponseDto> pacientes = pacienteService.traerPacientes();

        if(pacientes != null){
            return ResponseEntity.status(HttpStatus.OK).body(pacientes);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al traer pacientes ");
        }
    }

    @GetMapping("/traer-id/{id}")
    public ResponseEntity<?> traerPaciente(@PathVariable Long id){

        PacienteResponseDto pacienteResponseDto = pacienteService.traerPaciente(id);
        if(pacienteResponseDto != null){
            return ResponseEntity.status(HttpStatus.OK).body(pacienteResponseDto);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al traer Paciente con id: " + id);
        }
    }

    @GetMapping("/traer-dni/{dni}")
    public ResponseEntity<?> traerPacienteDni(@PathVariable String dni){

        PacienteResponseDto pacienteResponseDto = pacienteService.traerPacientePorDni(dni);
        if(pacienteResponseDto != null){
            return ResponseEntity.status(HttpStatus.OK).body(pacienteResponseDto);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al traer Paciente con  dni: " + dni);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Long id){

        pacienteService.eliminarPaciente(id);
        return ResponseEntity.noContent().build();
    }
}
