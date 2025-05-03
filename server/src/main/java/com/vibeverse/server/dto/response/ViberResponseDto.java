package com.vibeverse.server.dto.response;

import lombok.*;

import java.time.LocalDate; // Use java.time.LocalDate
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViberResponseDto {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String bio;
    private String profilePictureUrl;
    private LocalDate dateOfBirth; // Use java.time.LocalDate
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Password field is deliberately EXCLUDED for security reasons.
    // Never expose sensitive data like passwords (even hashed) in response DTOs.
}