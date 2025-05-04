package com.vibeverse.server.dto.mapper;

import com.vibeverse.server.dto.request.ViberBadgeRequestDto;
import com.vibeverse.server.dto.response.ViberBadgeResponseDto;
import com.vibeverse.server.model.ViberBadge;
import com.vibeverse.server.model.Viber;
import com.vibeverse.server.model.Badge;
import com.vibeverse.server.repository.ViberRepository;
import com.vibeverse.server.repository.BadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ViberBadgeMapper {

    private final ViberRepository viberRepository;
    private final BadgeRepository badgeRepository;

    // Convert Request DTO → Entity
    public ViberBadge toEntity(ViberBadgeRequestDto dto) {
        Viber viber = viberRepository.findById(dto.getViberId())
                .orElseThrow(() -> new IllegalArgumentException("Viber not found"));
        Badge badge = badgeRepository.findById(dto.getBadgeId())
                .orElseThrow(() -> new IllegalArgumentException("Badge not found"));

        return ViberBadge.builder()
                .viber(viber)
                .badge(badge)
                .build();
    }

    // Convert Entity → Response DTO
    public ViberBadgeResponseDto toResponseDto(ViberBadge entity) {
        return ViberBadgeResponseDto.builder()
                .viberBadgeId(entity.getViberBadgeId())
                .awardedAt(entity.getAwardedAt())
                .viberId(entity.getViber().getId())
                .badgeId(entity.getBadge().getBadgeId())
                .build();
    }
}