package com.abernathy.patient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PatientApplication {
	// TODO: 18/02/2021
	//Les utilisateurs peuvent annuler un rendez-vous sans mettre à jour leurs informations
	public static void main(String[] args) {
		SpringApplication.run(PatientApplication.class, args);
	}

}
