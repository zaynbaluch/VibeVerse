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
public class ViberBadgeDto {
    private Long id;
    private Long viberId;
    private String viberUsername;
    private Long badgeId;
    private String badgeName;
    private String badgeDescription;
    private String badgeImageUrl;
    private LocalDateTime awardedAt;
}
