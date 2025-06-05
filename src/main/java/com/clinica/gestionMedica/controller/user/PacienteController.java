package com.clinica.gestionMedica.controller.user;

import com.clinica.gestionMedica.dto.PacienteRequestDto;
import com.clinica.gestionMedica.dto.PacienteResponseDto;
import com.clinica.gestionMedica.dto.TurnoResponseDto;
import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.service.impl.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paciente")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @GetMapping("/historial/{id}") // probablemente deba modificar para poder usar #id == authentication.principal.id"
    public ResponseEntity<?> historialPaciente(@PathVariable Long id){

        List<TurnoResponseDto> historial = pacienteService.traerHistorial(id);
        return ResponseEntity.status(HttpStatus.OK).body(historial);

    }


    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarPaciente(@PathVariable Long id, @RequestBody PacienteRequestDto pacienteRequest){

        PacienteResponseDto pacienteEditado = pacienteService.editarPaciente(id, pacienteRequest);
        return ResponseEntity.status(HttpStatus.OK).body("Paciente editado exitosamente " + pacienteEditado);
    }
}
