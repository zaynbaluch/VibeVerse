package com.vibeverse.server.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViberResponseDto {

    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String bio;
    private String profilePictureUrl;
    private LocalDate dateOfBirth;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
