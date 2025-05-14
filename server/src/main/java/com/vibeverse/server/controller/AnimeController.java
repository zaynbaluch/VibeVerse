package com.vibeverse.server.controller;

import com.vibeverse.server.dto.AnimeDto;
import com.vibeverse.server.dto.MediaSearchDto;
import com.vibeverse.server.dto.MediaSearchResultDto;
import com.vibeverse.server.dto.ViberAnimeDto;
import com.vibeverse.server.dto.ViberAnimeUpdateDto;
import com.vibeverse.server.service.MediaService;
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
@RequestMapping("/api/anime")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Anime", description = "Anime API")
public class AnimeController {

    private final MediaService mediaService;

    @GetMapping("/library")
    @Operation(summary = "Get all anime in current user's library")
    public ResponseEntity<List<ViberAnimeDto>> getAllAnimeInUserLibrary() {
        List<ViberAnimeDto> animeList = mediaService.getAllViberAnimeForCurrentUser();
        return ResponseEntity.ok(animeList);
    }


    @PostMapping("/search")
    @Operation(summary = "Search anime")
    public ResponseEntity<MediaSearchResultDto<AnimeDto>> searchAnime(@Valid @RequestBody MediaSearchDto searchDto) {
        return ResponseEntity.ok(mediaService.searchAnime(searchDto));
    }
    
    @GetMapping("/{externalId}")
    @Operation(summary = "Get anime by external ID")
    public ResponseEntity<AnimeDto> getAnimeById(@PathVariable String externalId) {
        return ResponseEntity.ok(mediaService.getAnimeById(externalId));
    }
    
    @PostMapping("/{externalId}/add")
    @Operation(summary = "Add anime to library")
    public ResponseEntity<ViberAnimeDto> addAnimeToLibrary(
            @PathVariable String externalId,
            @Valid @RequestBody ViberAnimeUpdateDto updateDto) {
        System.out.println("Received add anime:" + updateDto);
        return new ResponseEntity<>(mediaService.addAnimeToLibrary(externalId, updateDto), HttpStatus.CREATED);
    }
    
    @PutMapping("/library/{id}")
    @Operation(summary = "Update anime in library")
    public ResponseEntity<ViberAnimeDto> updateAnimeInLibrary(
            @PathVariable Long id,
            @Valid @RequestBody ViberAnimeUpdateDto updateDto) {
        return ResponseEntity.ok(mediaService.updateAnimeInLibrary(id, updateDto));
    }
    
    @DeleteMapping("/library/{id}")
    @Operation(summary = "Remove anime from library")
    public ResponseEntity<Void> removeAnimeFromLibrary(@PathVariable Long id) {
        mediaService.removeAnimeFromLibrary(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}