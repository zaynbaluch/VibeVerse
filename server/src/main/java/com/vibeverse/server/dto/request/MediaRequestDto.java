package com.vibeverse.server.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull; // Import NotNull for the list
import jakarta.validation.constraints.Size; // Import Size
import lombok.*;

import java.util.List; // Import List
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaRequestDto {

    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 255) // Added Size constraint mirroring entity
    private String title;

    @Size(max = 1000) // Added Size constraint mirroring entity/DTO
    private String description;

    // @URL(message = "Image URL should be valid") // Optional: requires hibernate-validator-cdi dependency
    @Size(max = 255) // Added Size constraint mirroring entity
    private String imageUrl;

    // Changed type to List<String> to match the entity
    // @NotNull // Add if tags list should never be null
    // @Size(min = 0) // Example: require at least 0 tags
    private List<String> tags;

    // @NotNull // Add if specificData map should never be null
    private Map<String, Object> specificData;
}