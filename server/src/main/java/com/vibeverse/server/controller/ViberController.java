package com.vibeverse.server.controller;

import com.vibeverse.server.model.Viber;
import com.vibeverse.server.service.ViberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vibers")
@RequiredArgsConstructor
public class ViberController {

    private final ViberService viberService;

    @PostMapping
    public ResponseEntity<Viber> createViber(@Valid @RequestBody Viber viber) {
        Viber createdViber = viberService.createViber(viber);
        return new ResponseEntity<>(createdViber, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Viber> getViberById(@PathVariable Long id) {
        Viber viber = viberService.getViberById(id);
        return ResponseEntity.ok(viber);
    }

    @GetMapping
    public ResponseEntity<List<Viber>> getAllVibers() {
        List<Viber> vibers = viberService.getAllVibers();
        return ResponseEntity.ok(vibers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Viber> updateViber(@PathVariable Long id, @Valid @RequestBody Viber viberDetails) {
        Viber updatedViber = viberService.updateViber(id, viberDetails);
        return ResponseEntity.ok(updatedViber);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteViber(@PathVariable Long id) {
        viberService.deleteViber(id);
        return ResponseEntity.noContent().build();
    }
}
