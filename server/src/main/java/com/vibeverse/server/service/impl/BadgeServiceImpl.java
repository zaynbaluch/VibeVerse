package com.vibeverse.server.service.impl;

import com.vibeverse.server.dto.request.BadgeRequestDto;
import com.vibeverse.server.dto.response.BadgeResponseDto;
import com.vibeverse.server.mapper.BadgeMapper; // Import the mapper
import com.vibeverse.server.model.Badge;
import com.vibeverse.server.repository.BadgeRepository;
import com.vibeverse.server.service.BadgeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
// Removed unused imports like Collectors.toList() as MapStruct handles list mapping

@Service
@RequiredArgsConstructor // Injects final fields (BadgeRepository, BadgeMapper)
@Transactional // Apply transactional behavior to service methods by default
public class BadgeServiceImpl implements BadgeService {

    private final BadgeRepository badgeRepository; // Renamed repo for clarity
    private final BadgeMapper badgeMapper; // Inject the MapStruct mapper

    @Override
    public BadgeResponseDto createBadge(BadgeRequestDto req) {
        // Business logic: Check for duplicate name before creating
        if (badgeRepository.existsByName(req.getName())) {
            throw new IllegalArgumentException("Badge name already exists: " + req.getName());
        }

        // Use MapStruct to convert the Request DTO to the Badge entity
        Badge badge = badgeMapper.toEntity(req);

        // Save the new entity to the database
        Badge savedBadge = badgeRepository.save(badge);

        // Use MapStruct to convert the saved entity back to the Response DTO
        return badgeMapper.toDto(savedBadge);
    }

    @Override
    @Transactional(readOnly = true) // Read-only transaction for fetching data
    public List<BadgeResponseDto> getAllBadges() {
        // Fetch all entities
        List<Badge> badges = badgeRepository.findAll();
        // Use MapStruct to convert the list of entities to a list of Response DTOs
        return badgeMapper.toDtoList(badges);
    }

    @Override
    @Transactional(readOnly = true) // Read-only transaction for fetching data
    public BadgeResponseDto getBadgeById(UUID id) {
        // Find entity by ID, or throw EntityNotFoundException if not found
        Badge badge = badgeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Badge not found with ID: " + id));
        // Use MapStruct to convert the entity to a Response DTO
        return badgeMapper.toDto(badge);
    }

    @Override
    public BadgeResponseDto updateBadge(UUID id, BadgeRequestDto req) {
        // Find the existing entity by ID
        Badge existingBadge = badgeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Badge not found with ID: " + id));

        // Business logic: Check for duplicate name if the name is being changed
        if (!existingBadge.getName().equals(req.getName()) && badgeRepository.existsByName(req.getName())) {
            throw new IllegalArgumentException("Badge name already exists: " + req.getName());
        }

        // Use MapStruct to update the fields of the existing entity from the Request DTO
        // MapStruct will handle transferring fields based on the mapper definition
        badgeMapper.updateEntityFromDto(req, existingBadge);

        // Save the updated entity (JPA will perform an update because the entity is managed)
        // The @UpdateTimestamp on the entity will automatically be set here
        Badge updatedBadge = badgeRepository.save(existingBadge);

        // Use MapStruct to convert the updated entity back to the Response DTO
        return badgeMapper.toDto(updatedBadge);
    }

    @Override
    public void deleteBadge(UUID id) {
        // Check if the entity exists before deleting
        // This gives a specific EntityNotFoundException rather than potentially a different error
        if (!badgeRepository.existsById(id)) {
            throw new EntityNotFoundException("Badge not found with ID: " + id);
        }
        // Delete the entity by ID
        badgeRepository.deleteById(id);
    }

    // Manual mapping method (toDto) is removed as MapStruct generates the mapping logic.
}