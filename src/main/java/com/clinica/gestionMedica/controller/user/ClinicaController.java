package com.clinica.gestionMedica.controller.user;

import com.clinica.gestionMedica.dto.ClinicaRequestDto;
import com.clinica.gestionMedica.dto.ClinicaResponseDto;
import com.clinica.gestionMedica.service.IClinicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clinicas")
@RequiredArgsConstructor
public class ClinicaController {

    @Autowired
    private IClinicaService clinicaService;

    @PostMapping
    public ResponseEntity<ClinicaResponseDto> crearClinica(@RequestBody ClinicaRequestDto clinicaRequestDto) {
        return new ResponseEntity<>(clinicaService.crearClinica(clinicaRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClinicaResponseDto> obtenerClinicaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clinicaService.obtenerClinicaPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ClinicaResponseDto>> obtenerTodasLasClinicas() {
        return ResponseEntity.ok(clinicaService.obtenerTodasLasClinicas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClinicaResponseDto> actualizarClinica(@PathVariable Long id, @RequestBody ClinicaRequestDto clinicaRequestDto) {
        return ResponseEntity.ok(clinicaService.actualizarClinica(id, clinicaRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarClinica(@PathVariable Long id) {
        clinicaService.eliminarClinica(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{clinicaId}/pacientes/{pacienteId}")
    public ResponseEntity<Void> agregarPacienteAClinica(@PathVariable Long clinicaId, @PathVariable Long pacienteId) {
        clinicaService.agregarPacienteAClinica(clinicaId, pacienteId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{clinicaId}/pacientes/{pacienteId}")
    public ResponseEntity<Void> eliminarPacienteDeClinica(@PathVariable Long clinicaId, @PathVariable Long pacienteId) {
        clinicaService.eliminarPacienteDeClinica(clinicaId, pacienteId);
        return ResponseEntity.noContent().build();
    }
}