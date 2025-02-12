package com.clinica.gestionMedica.security.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String jwt;
    private String refreshToken;
}
