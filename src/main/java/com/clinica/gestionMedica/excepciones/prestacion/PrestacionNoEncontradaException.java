package com.clinica.gestionMedica.excepciones.prestacion;

public class PrestacionNoEncontradaException extends RuntimeException{

    public PrestacionNoEncontradaException(){

        super("Prestación no encontrada");
    }
}

