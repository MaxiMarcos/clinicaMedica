package com.clinica.gestionMedica.controller.admin;

import com.clinica.gestionMedica.dto.PacienteRequestDto;
import com.clinica.gestionMedica.dto.PacienteResponseDto;
import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.mapper.PacienteMapper;
import com.clinica.gestionMedica.repository.PacienteRepository;
import com.clinica.gestionMedica.service.impl.PacienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/pacientes")
@RequiredArgsConstructor
public class PacienteAdminController {

    private final PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<?> crearPaciente(@Valid @RequestBody PacienteRequestDto pacienteRequest){

        PacienteResponseDto pacienteResponseDto = pacienteService.crearPaciente(pacienteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteResponseDto);
    }

    @GetMapping
    public ResponseEntity<?> traerPacientes(){

        List<PacienteResponseDto> pacientes = pacienteService.traerPacientes();
        return ResponseEntity.status(HttpStatus.OK).body(pacientes);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> traerPaciente(@PathVariable Long id){

        PacienteResponseDto pacienteResponseDto = pacienteService.traerPaciente(id);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteResponseDto);

    }

    @GetMapping("/filtro")
    public ResponseEntity<?> traerPacienteDni(@PathVariable String dni){

        PacienteResponseDto pacienteResponseDto = pacienteService.traerPacientePorDni(dni);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteResponseDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editarPaciente(@PathVariable Long id, @RequestBody PacienteRequestDto pacienteRequest){

        PacienteResponseDto pacienteEditado = pacienteService.editarPaciente(id, pacienteRequest);
        return ResponseEntity.status(HttpStatus.OK).body("Paciente editado exitosamente " + pacienteEditado);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Long id){

        pacienteService.eliminarPaciente(id);
        return ResponseEntity.noContent().build();
    }
}
