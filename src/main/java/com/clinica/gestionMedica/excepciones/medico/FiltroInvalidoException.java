package com.clinica.gestionMedica.excepciones.medico;

public class FiltroInvalidoException extends RuntimeException{

    public FiltroInvalidoException(){

        super("Especialidad inválida.");
    }
}
