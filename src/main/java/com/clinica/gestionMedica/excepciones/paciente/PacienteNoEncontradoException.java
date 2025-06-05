package com.clinica.gestionMedica.excepciones.paciente;

public class PacienteNoEncontradoException extends RuntimeException{

    public PacienteNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}

