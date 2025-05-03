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
@RequiredArgsConstructor
public class ViberController {

    private final ViberService service;

    @PostMapping
    public ResponseEntity<ViberResponseDto> create(@Valid @RequestBody ViberRequestDto req) {
        ViberResponseDto dto = service.createViber(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<ViberResponseDto>> listAll() {
        return ResponseEntity.ok(service.getAllVibers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViberResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getViberById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ViberResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody ViberRequestDto req) {
        return ResponseEntity.ok(service.updateViber(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteViber(id);
        return ResponseEntity.noContent().build();
    }
}
