package com.clinica.gestionMedica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class GestionMedicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionMedicaApplication.class, args);
	}

}
