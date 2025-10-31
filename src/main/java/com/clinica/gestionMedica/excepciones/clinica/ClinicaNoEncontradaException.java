package com.clinica.gestionMedica.excepciones.clinica;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ClinicaNoEncontradaException extends RuntimeException {
    public ClinicaNoEncontradaException() {
        super("Clínica no encontrada");
    }

    public ClinicaNoEncontradaException(String message) {
        super(message);
    }
}