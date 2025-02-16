package com.example.gymspring.service.authService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import com.example.gymspring.config.JwtUtil;
import com.example.gymspring.model.auth.User;
import com.example.gymspring.repository.authRepository.UserRepository;

@Service
public class UserService {

	@Autowired
	private final UserRepository userRepository;
		
	@Autowired
	private final PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
			this.userRepository = userRepository;
			this.passwordEncoder = passwordEncoder;
			this.jwtUtil = jwtUtil;
	}

	//Enregistrer un utilisateur
	public User register(User user) {
		if (userRepository.findByUsername(user.getUsername()).isPresent()){
				throw new RuntimeException("Username already taken !");
			}
		if (userRepository.findByEmail(user.getEmail()).isPresent()){
				throw new RuntimeException("Email already taken !");
			}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	//Trouver un utilisateur par username
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	//Trouver un utilisateur par email
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public Optional<String> authenticate(String username, String password) {
		Optional<User> userOpt = userRepository.findByUsername(username);

		if (userOpt.isPresent()) {
			User user = userOpt.get();

		if (passwordEncoder.matches(password, user.getPassword())){
			String role = user.getRole().name(); //ROLE_ADMIN OU ROLE_EMPLOYEE
			return Optional.of(jwtUtil.generateToken(username, role));
		}
		}
		return Optional.empty();
	}

	//Obtenir tous les utilisateurs
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	//Supprimer un utilisateur (réservé à l'admin)
	public void deleteUser(Long id) {
		if (!userRepository.existsById(id)) {
			throw new EntityNotFoundException("Utilisateur non trouvé avec l'id " + id);
		}
		userRepository.deleteById(id);
	}
	}
