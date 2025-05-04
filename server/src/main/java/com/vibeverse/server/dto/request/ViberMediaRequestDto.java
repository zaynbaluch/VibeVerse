package com.vibeverse.server.dto.request;

import com.vibeverse.server.model.enums.MediaType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViberMediaRequestDto {

    @NotNull(message = "Media type is required")
    private MediaType type;

    @Min(value = 1, message = "Rating must be between 1-10")
    @Max(value = 10, message = "Rating must be between 1-10")
    private Integer rating;

    private String review;

    @DecimalMin(value = "0.0", message = "Progress must be between 0-1")
    @DecimalMax(value = "1.0", message = "Progress must be between 0-1")
    private Double progress;

    @NotNull(message = "Viber ID is required")
    private UUID viberId;

    @NotNull(message = "Media ID is required")
    private UUID mediaId;
}