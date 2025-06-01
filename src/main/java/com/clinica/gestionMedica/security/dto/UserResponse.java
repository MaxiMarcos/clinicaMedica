package com.clinica.gestionMedica.security.dto;

import com.clinica.gestionMedica.security.enumRole.RoleName;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String dni;
    private String email;
    @Enumerated(EnumType.STRING)
    private RoleName role;
}
