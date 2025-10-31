package com.clinica.gestionMedica.controller.user;

import com.clinica.gestionMedica.dto.PacienteRequestDto;
import com.clinica.gestionMedica.dto.PacienteResponseDto;
import com.clinica.gestionMedica.dto.TurnoResponseDto;
import com.clinica.gestionMedica.dto.ClinicaResponseDto;
import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.service.impl.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @GetMapping("/historial/{dni}")
    public ResponseEntity<?> historialPaciente(@PathVariable String dni){

        List<TurnoResponseDto> historial = pacienteService.traerHistorial(dni);
        return ResponseEntity.status(HttpStatus.OK).body(historial);

    }

    @GetMapping("/{pacienteId}/clinicas")
    public ResponseEntity<Set<ClinicaResponseDto>> obtenerClinicasPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(pacienteService.obtenerClinicasPorPaciente(pacienteId));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editarPaciente(@PathVariable Long id, @RequestBody PacienteRequestDto pacienteRequest){

        PacienteResponseDto pacienteEditado = pacienteService.editarPaciente(id, pacienteRequest);
        return ResponseEntity.status(HttpStatus.OK).body("Paciente editado exitosamente " + pacienteEditado);
    }
}
