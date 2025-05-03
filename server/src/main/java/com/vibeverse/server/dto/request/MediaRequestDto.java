package com.vibeverse.server.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaRequestDto {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private String imageUrl;

    // You might want to accept tags as a comma-separated string
    private String tags;

    private Map<String, Object> specificData;
}
