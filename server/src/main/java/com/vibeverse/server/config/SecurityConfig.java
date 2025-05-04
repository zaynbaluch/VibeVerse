package com.vibeverse.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Modern way to disable CSRF (non-deprecated)
                .csrf(AbstractHttpConfigurer::disable)

                // Session management (optional for JWT/stateless APIs)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        // Permit GET/POST to /api/vibers/**
                        .requestMatchers(HttpMethod.GET, "/api/vibers/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/vibers").permitAll()

                        // Permit other public endpoints (e.g., auth, media)
                        //.requestMatchers(HttpMethod.GET, "/api/media/**").permitAll()

                        // Require authentication for all other requests
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}