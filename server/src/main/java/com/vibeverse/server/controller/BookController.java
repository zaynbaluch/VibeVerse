package com.vibeverse.server.controller;

import com.vibeverse.server.dto.BookDto;
import com.vibeverse.server.dto.MediaSearchDto;
import com.vibeverse.server.dto.MediaSearchResultDto;
import com.vibeverse.server.dto.ViberBookDto;
import com.vibeverse.server.dto.ViberBookUpdateDto;
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
@RequestMapping("/api/books")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Books", description = "Books API")
public class BookController {

    private final MediaService mediaService;
    
    @PostMapping("/search")
    @Operation(summary = "Search books")
    public ResponseEntity<MediaSearchResultDto<BookDto>> searchBooks(@Valid @RequestBody MediaSearchDto searchDto) {
        return ResponseEntity.ok(mediaService.searchBooks(searchDto));
    }
    
    @GetMapping("/{externalId}")
    @Operation(summary = "Get book by external ID")
    public ResponseEntity<BookDto> getBookById(@PathVariable String externalId) {
        return ResponseEntity.ok(mediaService.getBookById(externalId));
    }
    
    @PostMapping("/{externalId}/add")
    @Operation(summary = "Add book to library")
    public ResponseEntity<ViberBookDto> addBookToLibrary(
            @PathVariable String externalId,
            @Valid @RequestBody ViberBookUpdateDto updateDto) {
        return new ResponseEntity<>(mediaService.addBookToLibrary(externalId, updateDto), HttpStatus.CREATED);
    }
    
    @PutMapping("/library/{id}")
    @Operation(summary = "Update book in library")
    public ResponseEntity<ViberBookDto> updateBookInLibrary(
            @PathVariable Long id,
            @Valid @RequestBody ViberBookUpdateDto updateDto) {
        return ResponseEntity.ok(mediaService.updateBookInLibrary(id, updateDto));
    }
    
    @DeleteMapping("/library/{id}")
    @Operation(summary = "Remove book from library")
    public ResponseEntity<Void> removeBookFromLibrary(@PathVariable Long id) {
        mediaService.removeBookFromLibrary(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
