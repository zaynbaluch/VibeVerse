package com.vibeverse.server.service;

import com.vibeverse.server.dto.request.BadgeRequestDto;
import com.vibeverse.server.dto.response.BadgeResponseDto;
import com.vibeverse.server.exception.ResourceNotFoundException;
import com.vibeverse.server.model.Badge;
import com.vibeverse.server.repository.BadgeRepository;
import com.vibeverse.server.dto.mapper.BadgeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BadgeService {

    private final BadgeRepository badgeRepository;
    private final BadgeMapper badgeMapper;

    public BadgeResponseDto createBadge(BadgeRequestDto dto) {
        if (badgeRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("Badge name already exists");
        }
        Badge badge = badgeMapper.toEntity(dto);
        return badgeMapper.toResponseDto(badgeRepository.save(badge));
    }

    @Transactional(readOnly = true)
    public BadgeResponseDto getBadgeById(UUID id) {
        Badge badge = badgeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Badge not found with id: " + id));
        return badgeMapper.toResponseDto(badge);
    }

    @Transactional(readOnly = true)
    public List<BadgeResponseDto> getAllBadges() {
        return badgeRepository.findAll().stream()
                .map(badgeMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public BadgeResponseDto updateBadge(UUID id, BadgeRequestDto dto) {
        Badge badge = badgeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Badge not found with id: " + id));

        badgeMapper.updateEntityFromDto(dto, badge);
        return badgeMapper.toResponseDto(badgeRepository.save(badge));
    }

    public void deleteBadge(UUID id) {
        if (!badgeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Badge not found with id: " + id);
        }
        badgeRepository.deleteById(id);
    }
}