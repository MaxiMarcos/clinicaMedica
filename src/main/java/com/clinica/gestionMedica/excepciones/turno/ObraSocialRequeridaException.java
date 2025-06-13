package com.clinica.gestionMedica.excepciones.turno;

public class ObraSocialRequeridaException extends RuntimeException {
    public ObraSocialRequeridaException(){

        super("Necesita obra social para reservar este turno");
    }
}