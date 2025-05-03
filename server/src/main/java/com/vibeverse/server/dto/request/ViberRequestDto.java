package com.vibeverse.server.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate; // Use java.time.LocalDate

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViberRequestDto {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "First name is required")
    @Size(max = 100) // Add max size constraint mirroring entity
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100) // Add max size constraint mirroring entity
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 255) // Add max size constraint mirroring entity
    private String email;

    // Password is required for creation. Updates should handle password separately.
    @NotBlank(message = "Password is required")
    private String password;

    @Size(max = 500, message = "Bio cannot exceed 500 characters")
    private String bio;

    // @URL(message = "Profile picture URL should be valid") // Optional: requires hibernate-validator-cdi dependency
    @Size(max = 255) // Add max size constraint mirroring entity
    private String profilePictureUrl;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth; // Use java.time.LocalDate
}