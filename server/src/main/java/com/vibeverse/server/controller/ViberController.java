package com.vibeverse.server.controller;

import com.vibeverse.server.dto.request.*;
import com.vibeverse.server.dto.response.*;
import com.vibeverse.server.model.enums.MediaType;
import com.vibeverse.server.model.enums.RequestStatus;
import com.vibeverse.server.service.ViberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/vibers")
@RequiredArgsConstructor
public class ViberController {

    private final ViberService viberService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ViberResponseDto createViber(@Valid @RequestBody ViberRequestDto dto) {
        return viberService.createViber(dto);
    }

    @GetMapping("/{id}")
    public ViberResponseDto getViber(@PathVariable UUID id) {
        return viberService.getViberById(id);
    }

    @GetMapping
    public List<ViberResponseDto> getAllVibers() {
        return viberService.getAllVibers();
    }

    @PutMapping("/{id}")
    public ViberResponseDto updateViber(
            @PathVariable UUID id,
            @Valid @RequestBody ViberRequestDto dto
    ) {
        return viberService.updateViber(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteViber(@PathVariable UUID id) {
        viberService.deleteViber(id);
    }

    @GetMapping("/username/{username}")
    public ViberResponseDto getViberByUsername(@PathVariable String username) {
        return viberService.getViberByUsername(username);
    }

    @GetMapping("/search")
    public List<ViberResponseDto> searchVibers(@RequestParam String query) {
        return viberService.searchVibers(query);
    }



//    private final ViberService viberService;

    /* ========== VIBE BOARD ENDPOINTS ========== */

    @PostMapping("/boards")
    @ResponseStatus(HttpStatus.CREATED)
    public VibeBoardResponseDto createBoard(
            @PathVariable UUID viberId,
            @Valid @RequestBody VibeBoardRequestDto dto
    ) {
        return viberService.createVibeBoard(viberId, dto);
    }

    @GetMapping("/boards")
    public List<VibeBoardResponseDto> getVibeBoards(@PathVariable UUID viberId) {
        return viberService.getVibeBoards(viberId);
    }

    @GetMapping("/boards/{boardId}")
    public VibeBoardResponseDto getBoard(
            @PathVariable UUID viberId,
            @PathVariable UUID boardId
    ) {
        return viberService.getVibeBoard(viberId, boardId);
    }

    @PutMapping("/boards/{boardId}")
    public VibeBoardResponseDto updateBoard(
            @PathVariable UUID viberId,
            @PathVariable UUID boardId,
            @Valid @RequestBody VibeBoardRequestDto dto
    ) {
        return viberService.updateVibeBoard(viberId, boardId, dto);
    }

    @DeleteMapping("/boards/{boardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBoard(
            @PathVariable UUID viberId,
            @PathVariable UUID boardId
    ) {
        viberService.deleteVibeBoard(viberId, boardId);
    }

    @PostMapping("/boards/{boardId}/media/{mediaId}")
    public VibeBoardResponseDto addMediaToBoard(
            @PathVariable UUID viberId,
            @PathVariable UUID boardId,
            @PathVariable UUID mediaId
    ) {
        return viberService.addMediaToBoard(viberId, boardId, mediaId);
    }

    @DeleteMapping("/boards/{boardId}/media/{mediaId}")
    public VibeBoardResponseDto removeMediaFromBoard(
            @PathVariable UUID viberId,
            @PathVariable UUID boardId,
            @PathVariable UUID mediaId
    ) {
        return viberService.removeMediaFromBoard(viberId, boardId, mediaId);
    }

    @PatchMapping("/boards/{boardId}/aura")
    public VibeBoardResponseDto updateAuraPoints(
            @PathVariable UUID viberId,
            @PathVariable UUID boardId,
            @RequestParam int points
    ) {
        return viberService.updateBoardAuraPoints(viberId, boardId, points);
    }



    // Add these to your existing ViberController
    @GetMapping("/{viberId}/badges")
    public List<ViberBadgeResponseDto> getViberBadges(@PathVariable UUID viberId) {
        return viberService.getViberBadges(viberId);
    }

    @GetMapping("/{viberId}/badges/{badgeAssignmentId}")
    public ViberBadgeResponseDto getViberBadge(
            @PathVariable UUID viberId,
            @PathVariable UUID badgeAssignmentId
    ) {
        return viberService.getViberBadge(viberId, badgeAssignmentId);
    }

    @PostMapping("/{viberId}/badges")
    @ResponseStatus(HttpStatus.CREATED)
    public ViberBadgeResponseDto awardBadge(
            @PathVariable UUID viberId,
            @Valid @RequestBody ViberBadgeRequestDto dto
    ) {
        return viberService.awardBadgeToViber(viberId, dto);
    }

    @DeleteMapping("/{viberId}/badges/{badgeAssignmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBadge(
            @PathVariable UUID viberId,
            @PathVariable UUID badgeAssignmentId
    ) {
        viberService.removeBadgeFromViber(viberId, badgeAssignmentId);
    }

    @GetMapping("/{viberId}/has-badge/{badgeId}")
    public boolean checkHasBadge(
            @PathVariable UUID viberId,
            @PathVariable UUID badgeId
    ) {
        return viberService.hasBadge(viberId, badgeId);
    }

    @GetMapping("/{viberId}/badge-count")
    public int getBadgeCount(@PathVariable UUID viberId) {
        return viberService.getViberBadgeCount(viberId);
    }


    /* ========== VIBE REQUEST ENDPOINTS ========== */

    @PostMapping("/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public VibeRequestResponseDto sendVibeRequest(@Valid @RequestBody VibeRequestRequestDto dto) {
        return viberService.createVibeRequest(dto);
    }

    @GetMapping("/requests/sent/{viberId}")
    public List<VibeRequestResponseDto> getSentRequests(@PathVariable UUID viberId) {
        return viberService.getSentRequests(viberId);
    }

    @GetMapping("/requests/received/{viberId}")
    public List<VibeRequestResponseDto> getReceivedRequests(@PathVariable UUID viberId) {
        return viberService.getReceivedRequests(viberId);
    }

    @GetMapping("/requests/{requestId}")
    public VibeRequestResponseDto getRequestById(@PathVariable UUID requestId) {
        return viberService.getVibeRequestById(requestId);
    }

    @PutMapping("/requests/{requestId}")
    public VibeRequestResponseDto updateRequest(
            @PathVariable UUID requestId,
            @Valid @RequestBody VibeRequestRequestDto dto
    ) {
        return viberService.updateVibeRequest(requestId, dto);
    }

    @DeleteMapping("/requests/{requestId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRequest(@PathVariable UUID requestId) {
        viberService.deleteVibeRequest(requestId);
    }

    @GetMapping("/requests/status")
    public List<VibeRequestResponseDto> getRequestsByStatus(
            @RequestParam UUID viberId,
            @RequestParam RequestStatus status
    ) {
        return viberService.getRequestsByStatus(viberId, status);
    }

    @GetMapping("/requests/exists")
    public boolean checkRequestExists(
            @RequestParam UUID senderId,
            @RequestParam UUID receiverId
    ) {
        return viberService.requestExists(senderId, receiverId);
    }



    /* ========== VIBER MEDIA ENDPOINTS ========== */

    @PostMapping("/media")
    @ResponseStatus(HttpStatus.CREATED)
    public ViberMediaResponseDto createViberMedia(@Valid @RequestBody ViberMediaRequestDto dto) {
        return viberService.createViberMedia(dto);
    }

    @GetMapping("/media/{viberMediaId}")
    public ViberMediaResponseDto getViberMedia(@PathVariable UUID viberMediaId) {
        return viberService.getViberMediaById(viberMediaId);
    }

    @GetMapping("/vibers/{viberId}/media")
    public List<ViberMediaResponseDto> getMediaByViber(@PathVariable UUID viberId) {
        return viberService.getMediaByViberId(viberId);
    }

    @GetMapping("/media/{mediaId}/vibers")
    public List<ViberMediaResponseDto> getVibersByMedia(@PathVariable UUID mediaId) {
        return viberService.getVibersByMediaId(mediaId);
    }

    @PutMapping("/media/{viberMediaId}")
    public ViberMediaResponseDto updateViberMedia(
            @PathVariable UUID viberMediaId,
            @Valid @RequestBody ViberMediaRequestDto dto
    ) {
        return viberService.updateViberMedia(viberMediaId, dto);
    }

    @DeleteMapping("/media/{viberMediaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteViberMedia(@PathVariable UUID viberMediaId) {
        viberService.deleteViberMedia(viberMediaId);
    }

    @GetMapping("/media/search")
    public List<ViberMediaResponseDto> searchViberMedia(
            @RequestParam(required = false) UUID viberId,
            @RequestParam(required = false) UUID mediaId,
            @RequestParam(required = false) MediaType type,
            @RequestParam(required = false) Integer minRating
    ) {
        return viberService.searchViberMedia(viberId, mediaId, type, minRating);
    }

}