package com.vibeverse.server.service;

import com.vibeverse.server.dto.LoginRequestDto;
import com.vibeverse.server.dto.RefreshTokenRequestDto;
import com.vibeverse.server.dto.RegisterRequestDto;
import com.vibeverse.server.dto.TokenResponseDto;
import com.vibeverse.server.entity.Viber;
import com.vibeverse.server.exception.BadRequestException;
import com.vibeverse.server.exception.UnauthorizedException;
import com.vibeverse.server.mapper.ViberMapper;
import com.vibeverse.server.repository.ViberRepository;
import com.vibeverse.server.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ViberRepository viberRepository;
    private final ViberMapper viberMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    
    @Transactional
    public void register(RegisterRequestDto registerRequest) {
        if (viberRepository.existsByUsername(registerRequest.getUsername())) {
            throw new BadRequestException("Username is already taken");
        }
        
        if (viberRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BadRequestException("Email is already in use");
        }
        
        Viber viber = viberMapper.toEntity(registerRequest);
        viber.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));
        
        viberRepository.save(viber);
    }
    
    @Transactional(readOnly = true)
    public TokenResponseDto login(LoginRequestDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );
        
        String username = authentication.getName();
        
        String accessToken = tokenProvider.createAccessToken(username);
        String refreshToken = tokenProvider.createRefreshToken(username);

        System.out.println("accessToken: " + accessToken);

        return TokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(3600) // 1 hour in seconds
                .build();
    }
    
    @Transactional(readOnly = true)
    public TokenResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new UnauthorizedException("Invalid refresh token");
        }
        
        String username = tokenProvider.getUsername(refreshToken);
        String accessToken = tokenProvider.createAccessToken(username);
        
        return TokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(3600) // 1 hour in seconds
                .build();
    }
}
