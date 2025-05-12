//package com.vibeverse.server.controller;
//
//import com.vibeverse.server.dto.VibeBoardCreateDto;
//import com.vibeverse.server.dto.VibeBoardDto;
//import com.vibeverse.server.dto.VibeBoardUpdateDto;
//import com.vibeverse.server.service.VibeBoardService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/boards")
//@RequiredArgsConstructor
//@SecurityRequirement(name = "bearerAuth")
//@Tag(name = "VibeBoards", description = "VibeBoard API")
//public class VibeBoardController {
//
//    private final VibeBoardService vibeBoardService;
//
//    @GetMapping("/me")
//    @Operation(summary = "Get current viber's boards")
//    public ResponseEntity<List<VibeBoardDto>> getCurrentViberBoards() {
//        return ResponseEntity.ok(vibeBoardService.getCurrentViberBoards());
//    }
//
//    @GetMapping("/viber/{viberId}")
//    @Operation(summary = "Get boards by viber ID")
//    public ResponseEntity<List<VibeBoardDto>> getViberBoardsByViberId(@PathVariable Long viberId) {
//        return ResponseEntity.ok(vibeBoardService.getViberBoardsByViberId(viberId));
//    }
//
//    @GetMapping("/{id}")
//    @Operation(summary = "Get board by ID")
//    public ResponseEntity<VibeBoardDto> getVibeBoardById(@PathVariable Long id) {
//        return ResponseEntity.ok(vibeBoardService.getVibeBoardById(id));
//    }
//
//    @PostMapping
//    @Operation(summary = "Create a new board")
//    public ResponseEntity<VibeBoardDto> createVibeBoard(@Valid @RequestBody VibeBoardCreateDto vibeBoardCreateDto) {
//        return new ResponseEntity<>(vibeBoardService.createVibeBoard(vibeBoardCreateDto), HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{id}")
//    @Operation(summary = "Update a board")
//    public ResponseEntity<VibeBoardDto> updateVibeBoard(
//            @PathVariable Long id,
//            @Valid @RequestBody VibeBoardUpdateDto vibeBoardUpdateDto) {
//        return ResponseEntity.ok(vibeBoardService.updateVibeBoard(id, vibeBoardUpdateDto));
//    }
//
//    @DeleteMapping("/{id}")
//    @Operation(summary = "Delete a board")
//    public ResponseEntity<Void> deleteVibeBoard(@PathVariable Long id) {
//        vibeBoardService.deleteVibeBoard(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//}


package com.vibeverse.server.controller;

import com.vibeverse.server.dto.*;
import com.vibeverse.server.entity.ViberMedia;
import com.vibeverse.server.service.VibeBoardService;
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
@RequestMapping("/api/vibe-boards")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "VibeBoards", description = "VibeBoards API")
public class VibeBoardController {

    private final VibeBoardService vibeBoardService;

    @GetMapping("/me")
    @Operation(summary = "Get current viber's boards")
    public ResponseEntity<List<VibeBoardDto>> getCurrentViberBoards() {
        return ResponseEntity.ok(vibeBoardService.getCurrentViberBoards());
    }

    @GetMapping("/viber/{viberId}")
    @Operation(summary = "Get viber's boards by viber ID")
    public ResponseEntity<List<VibeBoardDto>> getViberBoardsByViberId(@PathVariable Long viberId) {
        return ResponseEntity.ok(vibeBoardService.getViberBoardsByViberId(viberId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get vibe board by ID")
    public ResponseEntity<VibeBoardDto> getVibeBoardById(@PathVariable Long id) {
        return ResponseEntity.ok(vibeBoardService.getVibeBoardById(id));
    }

    @PostMapping
    @Operation(summary = "Create vibe board")
    public ResponseEntity<VibeBoardDto> createVibeBoard(@Valid @RequestBody VibeBoardCreateDto vibeBoardCreateDto) {
        return new ResponseEntity<>(vibeBoardService.createVibeBoard(vibeBoardCreateDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update vibe board")
    public ResponseEntity<VibeBoardDto> updateVibeBoard(
            @PathVariable Long id,
            @Valid @RequestBody VibeBoardUpdateDto vibeBoardUpdateDto) {
        return ResponseEntity.ok(vibeBoardService.updateVibeBoard(id, vibeBoardUpdateDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete vibe board")
    public ResponseEntity<Void> deleteVibeBoard(@PathVariable Long id) {
        vibeBoardService.deleteVibeBoard(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // New endpoints for managing media in boards

    @GetMapping("/{id}/media")
    @Operation(summary = "Get all media in a board")
    public ResponseEntity<List<VibeBoardMediaDto>> getBoardMedia(@PathVariable("id") Long boardId) {
        return ResponseEntity.ok(vibeBoardService.getBoardMedia(boardId));
    }

    @PostMapping("/{id}/media")
    @Operation(summary = "Add media to a board")
    public ResponseEntity<VibeBoardMediaDto> addMediaToBoard(
            @PathVariable("id") Long boardId,
            @Valid @RequestBody AddMediaToBoardDto addMediaDto) {
        return new ResponseEntity<>(vibeBoardService.addMediaToBoard(boardId, addMediaDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{boardId}/media/{mediaId}/type/{mediaType}")
    @Operation(summary = "Remove media from a board")
    public ResponseEntity<Void> removeMediaFromBoard(
            @PathVariable Long boardId,
            @PathVariable Long mediaId,
            @PathVariable ViberMedia.MediaType mediaType) {
        vibeBoardService.removeMediaFromBoard(boardId, mediaId, mediaType);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{boardId}/media/{mediaId}/type/{mediaType}/note")
    @Operation(summary = "Update media note in a board")
    public ResponseEntity<VibeBoardMediaDto> updateMediaNote(
            @PathVariable Long boardId,
            @PathVariable Long mediaId,
            @PathVariable ViberMedia.MediaType mediaType,
            @RequestBody String note) {
        return ResponseEntity.ok(vibeBoardService.updateMediaNote(boardId, mediaId, mediaType, note));
    }
}