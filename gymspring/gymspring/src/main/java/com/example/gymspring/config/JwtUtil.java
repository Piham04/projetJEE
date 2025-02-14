package com.example.gymspring.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
	private Key key;
		
	@Value("${jwt.secret}")
	private String secret;
	
	@PostConstruct
	public void init(){
		this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}

	public String generateToken(String username, String role) {
		return Jwts.builder()
			.setSubject(username)
			.claim("authorities", role)
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public String extractUsername(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
	}

	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}
	

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException e) {
			System.out.println("Token expiré");
		} catch (MalformedJwtException e) {
			System.out.println("Token invalide");
		} catch (io.jsonwebtoken.security.SecurityException e) {
			System.out.println("Signature invalide");
		} catch (Exception e) {
			System.out.println("Token invalide");
		}
		return false;
	}
}
