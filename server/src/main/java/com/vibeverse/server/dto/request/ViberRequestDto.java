package com.vibeverse.server.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.sql.Date;

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
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @Size(max = 500, message = "Bio cannot exceed 500 characters")
    private String bio;

    private String profilePictureUrl;

    @Past(message = "Date of birth must be in the past")
    private Date dateOfBirth;
}
