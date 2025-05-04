package com.vibeverse.server.dto.mapper;

import com.vibeverse.server.dto.request.MediaRequestDto;
import com.vibeverse.server.dto.response.MediaResponseDto;
import com.vibeverse.server.model.Media;
import org.springframework.stereotype.Component;

@Component
public class MediaMapper {

    // Convert Request DTO → Entity
    public Media toEntity(MediaRequestDto dto) {
        return Media.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .imageUrl(dto.getImageUrl())
                .tags(dto.getTags())
                .specificData(dto.getSpecificData())
                .build();
    }

    // Convert Entity → Response DTO
    public MediaResponseDto toResponseDto(Media entity) {
        return MediaResponseDto.builder()
                .mediaId(entity.getMediaId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .imageUrl(entity.getImageUrl())
                .tags(entity.getTags())
                .specificData(entity.getSpecificData())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    // Update Entity from Request DTO (null-safe)
    public void updateEntityFromDto(MediaRequestDto dto, Media entity) {
        if (dto.getTitle() != null) {
            entity.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }
        if (dto.getImageUrl() != null) {
            entity.setImageUrl(dto.getImageUrl());
        }
        if (dto.getTags() != null) {
            entity.setTags(dto.getTags());
        }
        if (dto.getSpecificData() != null) {
            entity.setSpecificData(dto.getSpecificData());
        }
    }
}