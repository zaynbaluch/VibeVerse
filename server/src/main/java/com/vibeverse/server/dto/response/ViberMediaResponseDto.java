package com.vibeverse.server.dto.response;

import com.vibeverse.server.model.enums.MediaType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViberMediaResponseDto {

    private UUID viberMediaId;
    private MediaType type;
    private Integer rating;
    private String review;
    private Double progress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID viberId;
    private String viberUsername;
    private UUID mediaId;
    private String mediaTitle;
}