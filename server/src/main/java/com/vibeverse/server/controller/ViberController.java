package com.vibeverse.server.controller;

import com.vibeverse.server.dto.PasswordUpdateDto;
import com.vibeverse.server.dto.ViberDto;
import com.vibeverse.server.dto.ViberUpdateDto;
import com.vibeverse.server.service.ViberService;
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
@RequestMapping("/api/vibers")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Vibers", description = "Viber API")
public class ViberController {

    private final ViberService viberService;
    
    @GetMapping("/me")
    @Operation(summary = "Get current viber profile")
    public ResponseEntity<ViberDto> getCurrentViber() {
        return ResponseEntity.ok(viberService.getCurrentViber());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get viber by ID")
    public ResponseEntity<ViberDto> getViberById(@PathVariable Long id) {
        return ResponseEntity.ok(viberService.getViberById(id));
    }
    
    @GetMapping("/username/{username}")
    @Operation(summary = "Get viber by username")
    public ResponseEntity<ViberDto> getViberByUsername(@PathVariable String username) {
        return ResponseEntity.ok(viberService.getViberByUsername(username));
    }
    
    @GetMapping("/search")
    @Operation(summary = "Search vibers by query")
    public ResponseEntity<List<ViberDto>> searchVibers(@RequestParam String query) {
        return ResponseEntity.ok(viberService.searchVibers(query));
    }
    
    @PutMapping("/me")
    @Operation(summary = "Update current viber profile")
    public ResponseEntity<ViberDto> updateViber(@Valid @RequestBody ViberUpdateDto viberUpdateDto) {
        return ResponseEntity.ok(viberService.updateViber(viberUpdateDto));
    }
    
    @PutMapping("/me/password")
    @Operation(summary = "Update current viber password")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody PasswordUpdateDto passwordUpdateDto) {
        viberService.updatePassword(passwordUpdateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @DeleteMapping("/me")
    @Operation(summary = "Delete current viber account")
    public ResponseEntity<Void> deleteViber() {
        viberService.deleteViber();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
