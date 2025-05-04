package com.vibeverse.server.dto.request;

import lombok.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViberBadgeRequestDto {

    @NotNull(message = "Viber ID is required")
    private UUID viberId;

    @NotNull(message = "Badge ID is required")
    private UUID badgeId;
}
