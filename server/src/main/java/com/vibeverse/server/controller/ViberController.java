package com.vibeverse.server.controller;

import com.vibeverse.server.dto.request.ViberRequestDto;
import com.vibeverse.server.dto.response.ViberResponseDto;
import com.vibeverse.server.service.ViberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vibers")
@RequiredArgsConstructor // Injects the ViberService
public class ViberController {

    private final ViberService viberService; // Renamed service variable

    @PostMapping
    public ResponseEntity<ViberResponseDto> create(@Valid @RequestBody ViberRequestDto req) {
        ViberResponseDto dto = viberService.createViber(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<ViberResponseDto>> listAll() {
        return ResponseEntity.ok(viberService.getAllVibers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViberResponseDto> getById(@PathVariable Long id) {
        // The service layer handles the EntityNotFoundException,
        // Spring will automatically translate it to a 404 Not Found response.
        return ResponseEntity.ok(viberService.getViberById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ViberResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody ViberRequestDto req) {
        // The service layer handles the EntityNotFoundException,
        // Spring will automatically translate it to a 404 Not Found response.
        return ResponseEntity.ok(viberService.updateViber(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // The service layer handles the EntityNotFoundException,
        // Spring will automatically translate it to a 404 Not Found response.
        viberService.deleteViber(id);
        return ResponseEntity.noContent().build(); // 204 No Content on successful deletion
    }
}