package com.clinica.gestionMedica.excepciones.medico;

public class MedicosNoEncontradosException extends RuntimeException{

    public MedicosNoEncontradosException() {

        super("No se encontró ningún médico");
    }

}
