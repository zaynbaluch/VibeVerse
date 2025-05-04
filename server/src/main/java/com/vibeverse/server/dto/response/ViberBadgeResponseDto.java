package com.vibeverse.server.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViberBadgeResponseDto {

    private UUID viberBadgeId;
    private LocalDateTime awardedAt;
    private UUID viberId;
    private UUID badgeId;
}
