package com.vibeverse.server.controller;

import com.vibeverse.server.dto.request.BadgeRequestDto;
import com.vibeverse.server.dto.response.BadgeResponseDto;
import com.vibeverse.server.service.BadgeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/badges")
@RequiredArgsConstructor // Injects the BadgeService
public class BadgeController {

    private final BadgeService badgeService; // Renamed service variable for clarity

    @PostMapping
    public ResponseEntity<BadgeResponseDto> create(@Valid @RequestBody BadgeRequestDto req) {
        return ResponseEntity
                .status(HttpStatus.CREATED) // Use HttpStatus.CREATED for successful creation
                .body(badgeService.createBadge(req));
    }

    @GetMapping
    public ResponseEntity<List<BadgeResponseDto>> listAll() {
        return ResponseEntity.ok(badgeService.getAllBadges()); // OK (200) is standard for GET
    }

    @GetMapping("/{id}")
    public ResponseEntity<BadgeResponseDto> getById(@PathVariable UUID id) {
        // The service layer handles EntityNotFoundException, which Spring translates to 404
        return ResponseEntity.ok(badgeService.getBadgeById(id)); // OK (200) is standard for GET
    }

    @PutMapping("/{id}")
    public ResponseEntity<BadgeResponseDto> update(
            @PathVariable UUID id,
            @Valid @RequestBody BadgeRequestDto req) {
        // The service layer handles EntityNotFoundException, which Spring translates to 404
        return ResponseEntity.ok(badgeService.updateBadge(id, req)); // OK (200) is standard for PUT success
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        // The service layer handles EntityNotFoundException, which Spring translates to 404
        badgeService.deleteBadge(id);
        return ResponseEntity.noContent().build(); // 204 No Content is standard for successful DELETE
    }
}