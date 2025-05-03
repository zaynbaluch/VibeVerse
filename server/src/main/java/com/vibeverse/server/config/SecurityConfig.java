package com.vibeverse.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // Import HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // Optional but good practice
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer; // Import for disabling csrf
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Optional but good practice to explicitly enable Spring Security
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Use a strong hashing algorithm like BCrypt
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // By default, CSRF is enabled. Disable only if you have a specific reason (e.g., stateless API with tokens)
                // For a typical web app, keep it enabled. If you have a stateless API, you might disable it safely.
                // For this example, we'll disable it, but be aware of the implications.
                .csrf(AbstractHttpConfigurer::disable) // Modern way to disable CSRF

                .authorizeHttpRequests(auth -> auth
                        // Permit POST requests to /api/vibers (e.g., for user registration)
                        .requestMatchers(HttpMethod.POST, "/api/vibers").permitAll()

                        // Permit other specific public endpoints (e.g., auth, media list if public)
                        // .requestMatchers("/api/auth/**").permitAll()
                        // .requestMatchers(HttpMethod.GET, "/api/media").permitAll()

                        // Require authentication for any other request
                        .anyRequest().authenticated()
                );
        // .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Consider STATELESS for token-based APIs

        return http.build();
    }

    // You might add configuration for form login, OAuth2, JWT handling, etc. here
}