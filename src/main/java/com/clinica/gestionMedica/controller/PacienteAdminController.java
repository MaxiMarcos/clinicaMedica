package com.clinica.gestionMedica.controller;

import com.clinica.gestionMedica.dto.PacienteDto;
import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.mapper.PacienteMapper;
import com.clinica.gestionMedica.service.impl.PacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paciente-admin")
public class PacienteAdminController {

    private final PacienteService pacienteService;
    private final PacienteMapper pacienteMapper;

    public PacienteAdminController(PacienteService pacienteService, PacienteMapper pacienteMapper) {
        this.pacienteService = pacienteService;
        this.pacienteMapper = pacienteMapper;
    }

    @GetMapping("/traertodo")
    public ResponseEntity<?> traerPacientes(){

        List<Paciente> pacientes = pacienteService.traerPacientes();
        List<PacienteDto> pacientesDTO = pacienteMapper.conversionPacientesDto(pacientes);

        if(pacientesDTO != null){
            return ResponseEntity.status(HttpStatus.OK).body(pacientesDTO);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al traer pacientes ");
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Long id){

        Paciente paciente = pacienteService.traerPaciente(id);
        if (paciente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente no encontrado con el ID: " + id);
        }
        pacienteService.eliminarPaciente(paciente.getId());
        return ResponseEntity.status(HttpStatus.OK).body("Paciente eliminado correctamente con el ID: " + id);
    }
}
