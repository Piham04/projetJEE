package com.example.gymspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.gymspring.config.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

	@Autowired
    private JwtUtil jwtUtil;
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/api/auth/**").permitAll()
				.requestMatchers("/api/customers/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_EMPLOYEE")
				//.requestMatchers("/api/packs/**").hasAnyAuthority("ROLE_ADMIN")
				//.requestMatchers("/api/subscriptions/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_EMPLOYEE")
				//.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
				.requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")
				.anyRequest().authenticated()
			)
			.addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)  // Ajout du filtre ici
			.exceptionHandling(ex -> ex.authenticationEntryPoint((request, response, authException) -> {
				System.out.println("🚨 Accès refusé : " + authException.getMessage());
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			}));
		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("http://localhost:4200"));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("*"));  // Accepte tous les headers
		configuration.setAllowCredentials(true);  // Autorise les credentials

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);  // Applique CORS à toutes les routes
		return source;
	}

}
