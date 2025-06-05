package com.clinica.gestionMedica.excepciones.medico;

public class MedicoNoEncontradoException extends RuntimeException {

    public MedicoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}