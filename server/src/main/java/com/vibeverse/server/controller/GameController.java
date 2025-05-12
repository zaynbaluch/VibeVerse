package com.vibeverse.server.controller;

import com.vibeverse.server.dto.GameDto;
import com.vibeverse.server.dto.MediaSearchDto;
import com.vibeverse.server.dto.MediaSearchResultDto;
import com.vibeverse.server.dto.ViberGameDto;
import com.vibeverse.server.dto.ViberGameUpdateDto;
import com.vibeverse.server.service.MediaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Games", description = "Games API")
public class GameController {

    private final MediaService mediaService;
    
    @PostMapping("/search")
    @Operation(summary = "Search games")
    public ResponseEntity<MediaSearchResultDto<GameDto>> searchGames(@Valid @RequestBody MediaSearchDto searchDto) {
        return ResponseEntity.ok(mediaService.searchGames(searchDto));
    }
    
    @GetMapping("/{externalId}")
    @Operation(summary = "Get game by external ID")
    public ResponseEntity<GameDto> getGameById(@PathVariable String externalId) {
        return ResponseEntity.ok(mediaService.getGameById(externalId));
    }
    
    @PostMapping("/{externalId}/add")
    @Operation(summary = "Add game to library")
    public ResponseEntity<ViberGameDto> addGameToLibrary(
            @PathVariable String externalId,
            @Valid @RequestBody ViberGameUpdateDto updateDto) {
        return new ResponseEntity<>(mediaService.addGameToLibrary(externalId, updateDto), HttpStatus.CREATED);
    }
    
    @PutMapping("/library/{id}")
    @Operation(summary = "Update game in library")
    public ResponseEntity<ViberGameDto> updateGameInLibrary(
            @PathVariable Long id,
            @Valid @RequestBody ViberGameUpdateDto updateDto) {
        return ResponseEntity.ok(mediaService.updateGameInLibrary(id, updateDto));
    }
    
    @DeleteMapping("/library/{id}")
    @Operation(summary = "Remove game from library")
    public ResponseEntity<Void> removeGameFromLibrary(@PathVariable Long id) {
        mediaService.removeGameFromLibrary(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
