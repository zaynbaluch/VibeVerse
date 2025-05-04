package com.vibeverse.server.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VibeBoardResponseDto {

    private UUID vboardId;
    private String name;
    private Integer auraPoints;
    private String description;
    private LocalDateTime createdAt;

    private List<UUID> mediaItemIds;
    private UUID viberId;
}
