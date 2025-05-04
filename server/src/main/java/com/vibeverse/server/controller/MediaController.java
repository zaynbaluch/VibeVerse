package com.vibeverse.server.controller;

import com.vibeverse.server.dto.request.MediaRequestDto;
import com.vibeverse.server.dto.response.MediaResponseDto;
import com.vibeverse.server.service.MediaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
public class MediaController {

    private final MediaService mediaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MediaResponseDto createMedia(@Valid @RequestBody MediaRequestDto dto) {
        return mediaService.createMedia(dto);
    }

    @GetMapping("/{id}")
    public MediaResponseDto getMedia(@PathVariable UUID id) {
        return mediaService.getMediaById(id);
    }

    @GetMapping
    public List<MediaResponseDto> getAllMedia() {
        return mediaService.getAllMedia();
    }

    @PutMapping("/{id}")
    public MediaResponseDto updateMedia(
            @PathVariable UUID id,
            @Valid @RequestBody MediaRequestDto dto
    ) {
        return mediaService.updateMedia(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMedia(@PathVariable UUID id) {
        mediaService.deleteMedia(id);
    }

    @GetMapping("/search")
    public List<MediaResponseDto> searchMedia(@RequestParam String title) {
        return mediaService.searchMediaByTitle(title);
    }

    @GetMapping("/with-images")
    public List<MediaResponseDto> getMediaWithImages() {
        return mediaService.getMediaWithImages();
    }
}