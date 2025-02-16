package com.example.gymspring.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

public class JwtFilter extends OncePerRequestFilter {
    
    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			try {
				Claims claims = jwtUtil.extractAllClaims(token);
				List<String> roles = claims.get("authorities", List.class);
				
				if (roles != null) {
					List<SimpleGrantedAuthority> authorities = roles.stream()
						.map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList());

					UserDetails userDetails = new User(claims.getSubject(), "", authorities);

					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities()
					);

					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			} catch (ExpiredJwtException e) {
				logger.error("Token expired", e);
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
				return;
			} catch (MalformedJwtException e) {
				logger.error("Invalid token", e);
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
				return;
			} catch (Exception e) {
				logger.error("Token processing error", e);
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token processing error");
				return;
			}
		}

		chain.doFilter(request, response);
	}

}
