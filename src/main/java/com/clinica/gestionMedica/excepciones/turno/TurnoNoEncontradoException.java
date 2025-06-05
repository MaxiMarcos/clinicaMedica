package com.clinica.gestionMedica.excepciones.turno;

public class TurnoNoEncontradoException extends RuntimeException {

    public TurnoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}