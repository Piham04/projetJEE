package com.example.gymspring.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Entity
@Table(name = "subscriptions")
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subscription {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "pack_id",nullable=false)
	private Pack pack;

	@ManyToOne
	@JoinColumn(name = "customer_id",nullable=false)
	private Customer customer;

	@FutureOrPresent(message = "La date de début doit être aujourd'hui ou dans le futur")
	@Column(nullable = false)
	private LocalDate startDate;
}


