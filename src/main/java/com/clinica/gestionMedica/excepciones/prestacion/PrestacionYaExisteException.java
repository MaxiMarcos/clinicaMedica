package com.clinica.gestionMedica.excepciones.prestacion;

import com.clinica.gestionMedica.enums.TipoPrestacion;

public class PrestacionYaExisteException extends RuntimeException{

    public PrestacionYaExisteException(TipoPrestacion tipo) {

        super("Ya existe una prestición creada de tipo: " + tipo );
    }
}

