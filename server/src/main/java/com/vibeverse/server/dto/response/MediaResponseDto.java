package com.vibeverse.server.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List; // Import List
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaResponseDto {

    private UUID mediaId;

    private String title;

    private String description;

    private String imageUrl;

    // Changed type to List<String> to match the entity
    private List<String> tags;

    private Map<String, Object> specificData;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}