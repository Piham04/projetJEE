package com.example.gymspring.controller.authController;

import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;

import com.example.gymspring.model.auth.User;
import com.example.gymspring.service.authService.UserService;

@RestController
@RequestMapping("api/auth")
public class AuthController {

	private final UserService userService;

	public AuthController(UserService userService) {
		this.userService = userService;
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/login")
	public Map<String, String> login(@RequestBody Map<String, String> request) {
		Optional<String> tokenOpt = userService.authenticate(request.get("username"), request.get("password"));
		if (tokenOpt.isPresent()) {
			User user = userService.findByUsername(request.get("username"))
								.orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
			return Map.of(
				"token", tokenOpt.get(),
				"role", user.getRole().name()
			);
		} else {
			throw new RuntimeException("Mauvais nom d'utilisateur ou mot de passe");
		}
	}


}
