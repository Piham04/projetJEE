package com.example.gymspring.controller.authController;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.gymspring.service.authService.UserService;
import com.example.gymspring.model.auth.User;


@RestController
@RequestMapping("api/admin")
@CrossOrigin(origins = "*")
public class AuthControllerAdmin {
	
	private final UserService userService;

	public AuthControllerAdmin(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/register")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public User register(@RequestBody User user) {
		return userService.register(user);
	}

	@DeleteMapping("/users/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	System.out.println("Authenticated user roles: " + authentication.getAuthorities());

		userService.deleteUser(id);
		return ResponseEntity.ok().build();
	}

}
