package com.example.gymspring.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;


@NoArgsConstructor
@Entity
@Table(name = "customer")
@Data
@AllArgsConstructor
@Builder
@ToString
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message="Le nom ne peut pas être vide")
	private String lastName;

	@NotBlank(message="Le prénom ne peut pas être vide")
	private String firstName;

	@Column(nullable = false,updatable = false)
	private LocalDate registrationDate;

	@NotBlank(message = "Le numéro de téléphone ne peut pas être vide")
	@Pattern(regexp = "^\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Numero de téléphone invalide")
	@Column(unique = true)
	private String phone;
	private boolean active;
	
}
