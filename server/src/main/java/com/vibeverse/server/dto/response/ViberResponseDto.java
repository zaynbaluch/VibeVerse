package com.vibeverse.server.dto.response;

import lombok.*;

import java.sql.Date;
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
    private Date dateOfBirth;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
