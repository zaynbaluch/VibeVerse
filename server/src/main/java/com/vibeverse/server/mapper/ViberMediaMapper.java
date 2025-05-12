package com.vibeverse.server.mapper;

import com.vibeverse.server.dto.request.ViberMediaRequestDto;
import com.vibeverse.server.dto.response.ViberMediaResponseDto;
import com.vibeverse.server.model.ViberMedia;
import com.vibeverse.server.model.Viber;
import com.vibeverse.server.model.Media;
import org.springframework.stereotype.Component;

@Component
public class ViberMediaMapper {

    // Convert Request DTO → Entity
    public ViberMedia toEntity(ViberMediaRequestDto dto, Viber viber, Media media) {
        return ViberMedia.builder()
                .type(dto.getType())
                .rating(dto.getRating())
                .review(dto.getReview())
                .progress(dto.getProgress())
                .viber(viber)
                .media(media)
                .build();
    }

    // Convert Entity → Response DTO
    public ViberMediaResponseDto toResponseDto(ViberMedia entity) {
        return ViberMediaResponseDto.builder()
                .viberMediaId(entity.getViberMediaId())
                .type(entity.getType())
                .rating(entity.getRating())
                .review(entity.getReview())
                .progress(entity.getProgress())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .viberId(entity.getViber().getId())
                .viberUsername(entity.getViber().getUsername())  // Assuming Viber has getUsername()
                .mediaId(entity.getMedia().getMediaId())
                .mediaTitle(entity.getMedia().getTitle())        // Assuming Media has getTitle()
                .build();
    }
}