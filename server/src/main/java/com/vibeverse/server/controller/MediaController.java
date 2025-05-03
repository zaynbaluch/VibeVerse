//package com.vibeverse.server.controller;
//
//import com.vibeverse.server.dto.request.MediaRequestDto;
//import com.vibeverse.server.dto.response.MediaResponseDto;
//import com.vibeverse.server.service.MediaService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
////@RestController
////@RequestMapping("/api/media")
////@RequiredArgsConstructor // Injects the MediaService
//public class MediaController {
//
////    private final MediaService mediaService; // Renamed variable for clarity
////
////    @PostMapping
////    public ResponseEntity<MediaResponseDto> createMedia(@Valid @RequestBody MediaRequestDto requestDto) {
////        MediaResponseDto createdMedia = mediaService.createMedia(requestDto);
////        return new ResponseEntity<>(createdMedia, HttpStatus.CREATED); // Use HttpStatus.CREATED
////    }
////
////    @GetMapping
////    public ResponseEntity<List<MediaResponseDto>> getAllMedia() {
////        List<MediaResponseDto> mediaList = mediaService.getAllMedia();
////        return ResponseEntity.ok(mediaList); // Use ResponseEntity.ok() for 200 OK
////    }
////
////    @GetMapping("/{mediaId}")
////    public ResponseEntity<MediaResponseDto> getMediaById(@PathVariable UUID mediaId) {
////        // Service layer throws EntityNotFoundException, Spring handles mapping to 404
////        MediaResponseDto media = mediaService.getMediaById(mediaId);
////        return ResponseEntity.ok(media); // Use ResponseEntity.ok() for 200 OK
////    }
////
////    @PutMapping("/{mediaId}")
////    public ResponseEntity<MediaResponseDto> updateMedia(@PathVariable UUID mediaId,
////                                                        @Valid @RequestBody MediaRequestDto requestDto) {
////        // Service layer throws EntityNotFoundException, Spring handles mapping to 404
////        MediaResponseDto updatedMedia = mediaService.updateMedia(mediaId, requestDto);
////        return ResponseEntity.ok(updatedMedia); // Use ResponseEntity.ok() for 200 OK
////    }
////
////    @DeleteMapping("/{mediaId}")
////    public ResponseEntity<Void> deleteMedia(@PathVariable UUID mediaId) {
////        // Service layer throws EntityNotFoundException, Spring handles mapping to 404
////        mediaService.deleteMedia(mediaId);
////        return ResponseEntity.noContent().build(); // Use ResponseEntity.noContent() for 204 No Content
////    }
//}