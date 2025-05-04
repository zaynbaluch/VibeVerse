package com.vibeverse.server.dto.mapper;

import com.vibeverse.server.dto.request.VibeBoardRequestDto;
import com.vibeverse.server.dto.response.VibeBoardResponseDto;
import com.vibeverse.server.model.VibeBoard;
import com.vibeverse.server.model.Media;
import com.vibeverse.server.model.Viber;
import com.vibeverse.server.repository.MediaRepository;
import com.vibeverse.server.repository.ViberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VibeBoardMapper {

    private final MediaRepository mediaRepository;
    private final ViberRepository viberRepository;

    // Convert Request DTO → Entity
    public VibeBoard toEntity(VibeBoardRequestDto dto) {
        VibeBoard entity = VibeBoard.builder()
                .name(dto.getName())
                .auraPoints(dto.getAuraPoints())
                .description(dto.getDescription())
                .build();

        // Fetch and set relationships
        List<Media> mediaItems = dto.getMediaItemIds().stream()
                .map(id -> mediaRepository.findById(id).orElseThrow())
                .collect(Collectors.toList());
        entity.setMediaItems(mediaItems);

        Viber viber = viberRepository.findById(dto.getViberId())
                .orElseThrow(() -> new IllegalArgumentException("Viber not found"));
        entity.setViber(viber);

        return entity;
    }

    // Convert Entity → Response DTO
    public VibeBoardResponseDto toResponseDto(VibeBoard entity) {
        return VibeBoardResponseDto.builder()
                .vboardId(entity.getId())
                .name(entity.getName())
                .auraPoints(entity.getAuraPoints())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .mediaItemIds(entity.getMediaItems().stream()
                        .map(Media::getMediaId)
                        .collect(Collectors.toList()))
                .viberId(entity.getViber().getId())
                .build();
    }

    // Update Entity from Request DTO
    public void updateEntityFromDto(VibeBoardRequestDto dto, VibeBoard entity) {
        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
        if (dto.getAuraPoints() != null) {
            entity.setAuraPoints(dto.getAuraPoints());
        }
        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }
        if (dto.getMediaItemIds() != null) {
            List<Media> mediaItems = dto.getMediaItemIds().stream()
                    .map(id -> mediaRepository.findById(id).orElseThrow())
                    .collect(Collectors.toList());
            entity.setMediaItems(mediaItems);
        }
        if (dto.getViberId() != null) {
            Viber viber = viberRepository.findById(dto.getViberId())
                    .orElseThrow(() -> new IllegalArgumentException("Viber not found"));
            entity.setViber(viber);
        }
    }
}