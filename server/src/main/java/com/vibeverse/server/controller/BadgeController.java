package com.vibeverse.server.controller;

import com.vibeverse.server.dto.request.BadgeRequestDto;
import com.vibeverse.server.dto.response.BadgeResponseDto;
import com.vibeverse.server.service.BadgeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/badges")
@RequiredArgsConstructor
public class BadgeController {

    private final BadgeService badgeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BadgeResponseDto createBadge(@Valid @RequestBody BadgeRequestDto dto) {
        return badgeService.createBadge(dto);
    }

    @GetMapping("/{id}")
    public BadgeResponseDto getBadge(@PathVariable UUID id) {
        return badgeService.getBadgeById(id);
    }

    @GetMapping
    public List<BadgeResponseDto> getAllBadges() {
        return badgeService.getAllBadges();
    }

    @PutMapping("/{id}")
    public BadgeResponseDto updateBadge(
            @PathVariable UUID id,
            @Valid @RequestBody BadgeRequestDto dto
    ) {
        return badgeService.updateBadge(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBadge(@PathVariable UUID id) {
        badgeService.deleteBadge(id);
    }
}