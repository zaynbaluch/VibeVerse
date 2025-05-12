package com.vibeverse.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VibeBoardDto {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private Long viberId;
    private String viberUsername;

}
