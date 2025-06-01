package com.clinica.gestionMedica.security.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private String dni;
    private String email;
    private String password;
}
