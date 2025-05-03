package com.vibeverse.server.service.impl;

import com.vibeverse.server.dto.request.BadgeRequestDto;
import com.vibeverse.server.dto.response.BadgeResponseDto;
import com.vibeverse.server.model.Badge;
import com.vibeverse.server.repository.BadgeRepository;
import com.vibeverse.server.service.BadgeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BadgeServiceImpl implements BadgeService {

    private final BadgeRepository repo;

    @Override
    public BadgeResponseDto createBadge(BadgeRequestDto req) {
        if (repo.existsByName(req.getName())) {
            throw new IllegalArgumentException("Badge name already exists: " + req.getName());
        }
        Badge badge = Badge.builder()
                .name(req.getName())
                .description(req.getDescription())
                .iconUrl(req.getIconUrl())
                .build();
        Badge saved = repo.save(badge);
        return toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BadgeResponseDto> getAllBadges() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BadgeResponseDto getBadgeById(UUID id) {
        Badge badge = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Badge not found: " + id));
        return toDto(badge);
    }

    @Override
    public BadgeResponseDto updateBadge(UUID id, BadgeRequestDto req) {
        Badge badge = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Badge not found: " + id));
        if (!badge.getName().equals(req.getName()) && repo.existsByName(req.getName())) {
            throw new IllegalArgumentException("Badge name already exists: " + req.getName());
        }
        badge.setName(req.getName());
        badge.setDescription(req.getDescription());
        badge.setIconUrl(req.getIconUrl());
        return toDto(repo.save(badge));
    }

    @Override
    public void deleteBadge(UUID id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Badge not found: " + id);
        }
        repo.deleteById(id);
    }

    private BadgeResponseDto toDto(Badge b) {
        return BadgeResponseDto.builder()
                .badgeId(b.getBadgeId())
                .name(b.getName())
                .description(b.getDescription())
                .iconUrl(b.getIconUrl())
                .createdAt(b.getCreatedAt())
                .updatedAt(b.getUpdatedAt())
                .build();
    }
}
