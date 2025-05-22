package com.clinica.gestionMedica.security.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private int dni;
    private String email;
    private String password;
}
