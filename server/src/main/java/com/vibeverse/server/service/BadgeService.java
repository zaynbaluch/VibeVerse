package com.vibeverse.server.service;

import com.vibeverse.server.dto.request.BadgeRequestDto;
import com.vibeverse.server.dto.response.BadgeResponseDto;

import java.util.List;
import java.util.UUID;

public interface BadgeService {

    BadgeResponseDto createBadge(BadgeRequestDto request);

    List<BadgeResponseDto> getAllBadges();

    BadgeResponseDto getBadgeById(UUID badgeId);

    BadgeResponseDto updateBadge(UUID badgeId, BadgeRequestDto request);

    void deleteBadge(UUID badgeId);
}
