package com.clinica.gestionMedica.excepciones.medico;

public class MedicoNoEncontradoException extends RuntimeException {

    public MedicoNoEncontradoException() {

        super("Medico no encontrado");
    }
}