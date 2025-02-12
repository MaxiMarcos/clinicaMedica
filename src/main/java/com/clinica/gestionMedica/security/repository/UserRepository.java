package com.clinica.gestionMedica.security.repository;

import com.clinica.gestionMedica.security.dto.AuthResponse;
import com.clinica.gestionMedica.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<AuthResponse> findAllBy();

    Optional<User> findByEmail(String email);
}
