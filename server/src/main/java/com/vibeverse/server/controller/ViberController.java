package com.vibeverse.server.controller;

import com.vibeverse.server.dto.request.VibeBoardRequestDto;
import com.vibeverse.server.dto.request.ViberRequestDto;
import com.vibeverse.server.dto.response.VibeBoardResponseDto;
import com.vibeverse.server.dto.response.ViberResponseDto;
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
}