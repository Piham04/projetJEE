package com.example.gymspring.model.auth;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Entity
@Table(name = "users")
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true,nullable = false)
	private String username;

	@Email(message = "Email invalide")
	@Column(unique = true,nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	@Builder.Default
	private Boolean active = true;
	public enum Role{
		ADMIN,
		EMPLOYEE
	}
}
