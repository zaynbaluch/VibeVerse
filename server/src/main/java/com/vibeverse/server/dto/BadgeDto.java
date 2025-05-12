package com.vibeverse.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BadgeDto {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
}
