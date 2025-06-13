package com.clinica.gestionMedica.excepciones;

import com.clinica.gestionMedica.excepciones.medico.MedicoNoEncontradoException;
import com.clinica.gestionMedica.excepciones.medico.MedicoYaExisteException;
import com.clinica.gestionMedica.excepciones.paciente.PacienteNoEncontradoException;
import com.clinica.gestionMedica.excepciones.prestacion.PrestacionNoCubiertaException;
import com.clinica.gestionMedica.excepciones.prestacion.PrestacionNoEncontradaException;
import com.clinica.gestionMedica.excepciones.prestacion.PrestacionYaExisteException;
import com.clinica.gestionMedica.excepciones.turno.ObraSocialRequeridaException;
import com.clinica.gestionMedica.excepciones.turno.TurnoNoDisponibleException;
import com.clinica.gestionMedica.excepciones.turno.TurnoNoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    private Map<String, Object> buildBody(String message, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", message);
        body.put("path", request.getDescription(false));
        return body;
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> ResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT) // 409
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(MedicoYaExisteException.class)
    public ResponseEntity<Object> handleMedicoYaExisteException(MedicoYaExisteException ex, WebRequest request) {
        return new ResponseEntity<>(buildBody(ex.getMessage(), request), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MedicoNoEncontradoException.class)
    public ResponseEntity<Object> handleMedicoNoEncontradoException(MedicoNoEncontradoException ex, WebRequest request) {
        return new ResponseEntity<>(buildBody(ex.getMessage(), request), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PacienteNoEncontradoException.class)
    public ResponseEntity<Object> handlePacienteNoEncontradoException(PacienteNoEncontradoException ex, WebRequest request) {
        return new ResponseEntity<>(buildBody(ex.getMessage(), request), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PrestacionYaExisteException.class)
    public ResponseEntity<Object> handlePrestacionYaExisteException(PrestacionYaExisteException ex, WebRequest request) {
        return new ResponseEntity<>(buildBody(ex.getMessage(), request), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PrestacionNoEncontradaException.class)
    public ResponseEntity<Object> handlePrestacionNoEncontradaException(PrestacionNoEncontradaException ex, WebRequest request) {
        return new ResponseEntity<>(buildBody(ex.getMessage(), request), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TurnoNoEncontradoException.class)
    public ResponseEntity<Object> handleTurnoNoEncontradoException(TurnoNoEncontradoException ex, WebRequest request) {
        return new ResponseEntity<>(buildBody(ex.getMessage(), request), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PrestacionNoCubiertaException.class)
    public ResponseEntity<Object> handlePrestacionNoCubiertaException(PrestacionNoCubiertaException ex, WebRequest request) {
        return new ResponseEntity<>(buildBody(ex.getMessage(), request), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ObraSocialRequeridaException.class)
    public ResponseEntity<Object> handleObraSocialRequeridaException(ObraSocialRequeridaException ex, WebRequest request) {
        return new ResponseEntity<>(buildBody(ex.getMessage(), request), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TurnoNoDisponibleException.class)
    public ResponseEntity<Object> handleTurnoNoDisponibleException(TurnoNoDisponibleException ex, WebRequest request) {
        return new ResponseEntity<>(buildBody(ex.getMessage(), request), HttpStatus.BAD_REQUEST);
    }

}