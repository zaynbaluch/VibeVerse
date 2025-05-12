package com.vibeverse.server.controller;

import com.vibeverse.server.dto.VibeRequestCreateDto;
import com.vibeverse.server.dto.VibeRequestDto;
import com.vibeverse.server.dto.VibeRequestUpdateDto;
import com.vibeverse.server.service.VibeRequestService;
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
@RequestMapping("/api/requests")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "VibeRequests", description = "VibeRequest API")
public class VibeRequestController {

    private final VibeRequestService vibeRequestService;
    
    @GetMapping("/sent")
    @Operation(summary = "Get sent requests")
    public ResponseEntity<List<VibeRequestDto>> getSentRequests() {
        return ResponseEntity.ok(vibeRequestService.getSentRequests());
    }
    
    @GetMapping("/received")
    @Operation(summary = "Get received requests")
    public ResponseEntity<List<VibeRequestDto>> getReceivedRequests() {
        return ResponseEntity.ok(vibeRequestService.getReceivedRequests());
    }
    
    @GetMapping("/received/pending")
    @Operation(summary = "Get pending received requests")
    public ResponseEntity<List<VibeRequestDto>> getPendingReceivedRequests() {
        return ResponseEntity.ok(vibeRequestService.getPendingReceivedRequests());
    }
    
    @PostMapping
    @Operation(summary = "Send a friend request")
    public ResponseEntity<VibeRequestDto> sendRequest(@Valid @RequestBody VibeRequestCreateDto vibeRequestCreateDto) {
        return new ResponseEntity<>(vibeRequestService.sendRequest(vibeRequestCreateDto), HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Respond to a friend request")
    public ResponseEntity<VibeRequestDto> respondToRequest(
            @PathVariable Long id,
            @Valid @RequestBody VibeRequestUpdateDto vibeRequestUpdateDto) {
        return ResponseEntity.ok(vibeRequestService.respondToRequest(id, vibeRequestUpdateDto));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Cancel a friend request")
    public ResponseEntity<Void> cancelRequest(@PathVariable Long id) {
        vibeRequestService.cancelRequest(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
