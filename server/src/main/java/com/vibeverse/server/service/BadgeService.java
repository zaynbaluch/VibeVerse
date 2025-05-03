package com.vibeverse.server.service;

import com.vibeverse.server.dto.request.BadgeRequestDto;
import com.vibeverse.server.dto.response.BadgeResponseDto;

import java.util.List;
import java.util.UUID;

public interface BadgeService {

    /**
     * Creates a new Badge.
     *
     * @param request The DTO containing badge details.
     * @return The created Badge as a Response DTO.
     * @throws IllegalArgumentException if a badge with the same name already exists.
     */
    BadgeResponseDto createBadge(BadgeRequestDto request);

    /**
     * Retrieves all Badges.
     *
     * @return A list of all Badges as Response DTOs.
     */
    List<BadgeResponseDto> getAllBadges();

    /**
     * Retrieves a Badge by its ID.
     *
     * @param badgeId The ID of the Badge.
     * @return The Badge as a Response DTO.
     * @throws jakarta.persistence.EntityNotFoundException if the badge with the given ID is not found.
     */
    BadgeResponseDto getBadgeById(UUID badgeId);

    /**
     * Updates an existing Badge.
     *
     * @param badgeId The ID of the Badge to update.
     * @param request The DTO containing updated badge details.
     * @return The updated Badge as a Response DTO.
     * @throws jakarta.persistence.EntityNotFoundException if the badge with the given ID is not found.
     * @throws IllegalArgumentException if the new badge name already exists for a different badge.
     */
    BadgeResponseDto updateBadge(UUID badgeId, BadgeRequestDto request);

    /**
     * Deletes a Badge by its ID.
     *
     * @param badgeId The ID of the Badge to delete.
     * @throws jakarta.persistence.EntityNotFoundException if the badge with the given ID is not found.
     */
    void deleteBadge(UUID badgeId);
}