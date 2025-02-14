package com.example.gymspring.repository.authRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gymspring.model.auth.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
}