package com.clinica.gestionMedica.excepciones.prestacion;

public class PrestacionNoEncontradaException extends RuntimeException{

    public PrestacionNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}

