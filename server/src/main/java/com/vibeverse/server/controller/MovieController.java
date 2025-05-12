package com.vibeverse.server.controller;

import com.vibeverse.server.dto.MediaSearchDto;
import com.vibeverse.server.dto.MediaSearchResultDto;
import com.vibeverse.server.dto.MovieDto;
import com.vibeverse.server.dto.ViberMovieDto;
import com.vibeverse.server.dto.ViberMovieUpdateDto;
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
@RequestMapping("/api/movies")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Movies", description = "Movies API")
public class MovieController {

    private final MediaService mediaService;
    
    @PostMapping("/search")
    @Operation(summary = "Search movies")
    public ResponseEntity<MediaSearchResultDto<MovieDto>> searchMovies(@Valid @RequestBody MediaSearchDto searchDto) {
        return ResponseEntity.ok(mediaService.searchMovies(searchDto));
    }
    
    @GetMapping("/{externalId}")
    @Operation(summary = "Get movie by external ID")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable String externalId) {
        System.out.println(externalId);
        return ResponseEntity.ok(mediaService.getMovieById(externalId));
    }
    
    @PostMapping("/{externalId}/add")
    @Operation(summary = "Add movie to library")
    public ResponseEntity<ViberMovieDto> addMovieToLibrary(
            @PathVariable String externalId,
            @Valid @RequestBody ViberMovieUpdateDto updateDto) {
        return new ResponseEntity<>(mediaService.addMovieToLibrary(externalId, updateDto), HttpStatus.CREATED);
    }
    
    @PutMapping("/library/{id}")
    @Operation(summary = "Update movie in library")
    public ResponseEntity<ViberMovieDto> updateMovieInLibrary(
            @PathVariable Long id,
            @Valid @RequestBody ViberMovieUpdateDto updateDto) {
        return ResponseEntity.ok(mediaService.updateMovieInLibrary(id, updateDto));
    }
    
    @DeleteMapping("/library/{id}")
    @Operation(summary = "Remove movie from library")
    public ResponseEntity<Void> removeMovieFromLibrary(@PathVariable Long id) {
        mediaService.removeMovieFromLibrary(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
