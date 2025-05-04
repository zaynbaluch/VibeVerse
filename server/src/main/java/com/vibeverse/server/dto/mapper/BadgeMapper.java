package com.vibeverse.server.dto.mapper;

import com.vibeverse.server.dto.request.BadgeRequestDto;
import com.vibeverse.server.dto.response.BadgeResponseDto;
import com.vibeverse.server.model.Badge;
import org.springframework.stereotype.Component;

@Component
public class BadgeMapper {

    // Convert Request DTO → Entity
    public Badge toEntity(BadgeRequestDto dto) {
        return Badge.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .iconUrl(dto.getIconUrl())
                .build();
    }

    // Convert Entity → Response DTO
    public BadgeResponseDto toResponseDto(Badge entity) {
        return BadgeResponseDto.builder()
                .badgeId(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .iconUrl(entity.getIconUrl())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    // Update Entity from Request DTO
    public void updateEntityFromDto(BadgeRequestDto dto, Badge entity) {
        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }
        if (dto.getIconUrl() != null) {
            entity.setIconUrl(dto.getIconUrl());
        }
    }
}