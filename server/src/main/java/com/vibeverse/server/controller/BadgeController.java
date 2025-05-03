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
@RequiredArgsConstructor
public class BadgeController {

    private final BadgeService service;

    @PostMapping
    public ResponseEntity<BadgeResponseDto> create(@Valid @RequestBody BadgeRequestDto req) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createBadge(req));
    }

    @GetMapping
    public ResponseEntity<List<BadgeResponseDto>> listAll() {
        return ResponseEntity.ok(service.getAllBadges());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BadgeResponseDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getBadgeById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BadgeResponseDto> update(
            @PathVariable UUID id,
            @Valid @RequestBody BadgeRequestDto req) {
        return ResponseEntity.ok(service.updateBadge(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteBadge(id);
        return ResponseEntity.noContent().build();
    }
}
