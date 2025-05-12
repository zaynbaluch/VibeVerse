package com.vibeverse.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViberDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private Integer age;
    private String bio;
    private String profilePic;
}
