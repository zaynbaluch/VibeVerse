package com.vibeverse.server.controller;

import com.vibeverse.server.dto.LoginRequestDto;
import com.vibeverse.server.dto.RefreshTokenRequestDto;
import com.vibeverse.server.dto.RegisterRequestDto;
import com.vibeverse.server.dto.TokenResponseDto;
import com.vibeverse.server.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication API")
public class AuthController {

    private final AuthService authService;
    
    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequestDto registerRequest) {
        authService.register(registerRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @PostMapping("/login")
    @Operation(summary = "Login and get access token")
    public ResponseEntity<TokenResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        System.out.println("Received login request: " + loginRequest);
        TokenResponseDto tokenResponse = authService.login(loginRequest);
        return ResponseEntity.ok(tokenResponse);
    }
    
    @PostMapping("/refresh")
    @Operation(summary = "Refresh access token")
    public ResponseEntity<TokenResponseDto> refreshToken(@Valid @RequestBody RefreshTokenRequestDto refreshTokenRequest) {
        TokenResponseDto tokenResponse = authService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok(tokenResponse);
    }
}
