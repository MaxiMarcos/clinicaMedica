package com.clinica.gestionMedica.security.service;

import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.security.config.TokenResponse;
import com.clinica.gestionMedica.security.dto.AuthResponse;
import com.clinica.gestionMedica.security.dto.LoginRequest;
import com.clinica.gestionMedica.security.dto.RegisterRequest;
import com.clinica.gestionMedica.security.dto.UserResponse;
import com.clinica.gestionMedica.security.enumRole.RoleName;

import java.util.List;

public interface IAuthService {

    TokenResponse register(RegisterRequest registerRequest, RoleName roleName);
    TokenResponse login (LoginRequest loginRequest);
    TokenResponse refreshToken (String authHeader);

    List<UserResponse> findAll();
}
