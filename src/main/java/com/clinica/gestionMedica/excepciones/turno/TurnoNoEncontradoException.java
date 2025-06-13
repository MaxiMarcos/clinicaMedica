package com.clinica.gestionMedica.excepciones.turno;

public class TurnoNoEncontradoException extends RuntimeException {

    public TurnoNoEncontradoException() {

        super("Turno no encontrado.");
    }
}