package com.example.gymspring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
@Entity
@Table(name = "packs")
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pack {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Le nom de l'offre ne peut pas être vide")
	@Column(unique = true, nullable = false)
	private String offerName;

	@Column(nullable = false)
	@Min(1)
	private int durationMonths;

	@Column(nullable = false)
	@DecimalMin("0.0")
	private double monthlyPrice;

	@Column(nullable = false, length = 500)
	private String description;

	@Column(nullable = false)
	@Builder.Default
	private Boolean active = true;
}
