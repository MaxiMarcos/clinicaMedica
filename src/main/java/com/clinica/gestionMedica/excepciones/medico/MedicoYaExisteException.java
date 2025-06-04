package com.clinica.gestionMedica.excepciones.medico;

public class MedicoYaExisteException extends RuntimeException {

    public MedicoYaExisteException(String mensaje) {
        super(mensaje);
    }
}