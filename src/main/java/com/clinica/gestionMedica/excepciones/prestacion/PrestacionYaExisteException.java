package com.clinica.gestionMedica.excepciones.prestacion;

public class PrestacionYaExisteException extends RuntimeException{

    public PrestacionYaExisteException(String mensaje) {
        super(mensaje);
    }
}

