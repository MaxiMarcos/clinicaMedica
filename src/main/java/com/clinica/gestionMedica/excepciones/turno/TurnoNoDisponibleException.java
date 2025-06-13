package com.clinica.gestionMedica.excepciones.turno;

public class TurnoNoDisponibleException extends RuntimeException {
    public TurnoNoDisponibleException() {
        super("Este turno ya no está disponible.");
    }
}