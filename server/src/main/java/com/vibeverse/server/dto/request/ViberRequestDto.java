package com.vibeverse.server.dto.request;

import lombok.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViberRequestDto {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank(message = "First name is required")
    @Size(max = 100)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100)
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email
    @Size(max = 255)
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @Size(max = 500)
    private String bio;

    @Size(max = 255)
    private String profilePictureUrl;

    @Past
    private LocalDate dateOfBirth;
}
