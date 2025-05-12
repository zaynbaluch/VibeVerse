package com.vibeverse.server.controller;

import com.vibeverse.server.dto.BadgeDto;
import com.vibeverse.server.dto.ViberBadgeDto;
import com.vibeverse.server.service.BadgeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/badges")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Badges", description = "Badge API")
public class BadgeController {

    private final BadgeService badgeService;
    
    @GetMapping
    @Operation(summary = "Get all badges")
    public ResponseEntity<List<BadgeDto>> getAllBadges() {
        return ResponseEntity.ok(badgeService.getAllBadges());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get badge by ID")
    public ResponseEntity<BadgeDto> getBadgeById(@PathVariable Long id) {
        return ResponseEntity.ok(badgeService.getBadgeById(id));
    }
    
    @GetMapping("/viber/{viberId}")
    @Operation(summary = "Get viber's badges")
    public ResponseEntity<List<ViberBadgeDto>> getViberBadges(@PathVariable Long viberId) {
        return ResponseEntity.ok(badgeService.getViberBadges(viberId));
    }
    
    @PostMapping
    @Operation(summary = "Create a new badge")
    public ResponseEntity<BadgeDto> createBadge(@Valid @RequestBody BadgeDto badgeDto) {
        return new ResponseEntity<>(badgeService.createBadge(badgeDto), HttpStatus.CREATED);
    }
    
    @PostMapping("/viber/{viberId}/badge/{badgeId}")
    @Operation(summary = "Award a badge to a viber")
    public ResponseEntity<ViberBadgeDto> awardBadge(
            @PathVariable Long viberId,
            @PathVariable Long badgeId) {
        return new ResponseEntity<>(badgeService.awardBadge(viberId, badgeId), HttpStatus.CREATED);
    }
    
    @DeleteMapping("/viber/{viberId}/badge/{badgeId}")
    @Operation(summary = "Revoke a badge from a viber")
    public ResponseEntity<Void> revokeBadge(
            @PathVariable Long viberId,
            @PathVariable Long badgeId) {
        badgeService.revokeBadge(viberId, badgeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
