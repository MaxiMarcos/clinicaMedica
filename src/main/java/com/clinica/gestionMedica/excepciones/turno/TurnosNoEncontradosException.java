package com.clinica.gestionMedica.excepciones.turno;

public class TurnosNoEncontradosException extends RuntimeException{

    public TurnosNoEncontradosException(){

        super("No se encontraron turnos");
    }
}
