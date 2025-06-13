package com.clinica.gestionMedica.excepciones.prestacion;

public class PrestacionNoCubiertaException extends RuntimeException {
    public PrestacionNoCubiertaException() {

        super("Su obra social no cubre el estudio. Comuníquese con administración.");
    }
}