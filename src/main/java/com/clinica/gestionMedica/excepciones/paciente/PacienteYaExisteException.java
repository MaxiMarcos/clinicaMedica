package com.clinica.gestionMedica.excepciones.paciente;

public class PacienteYaExisteException extends RuntimeException {

    public PacienteYaExisteException(String mensaje) {

        super(mensaje);
    }
}
