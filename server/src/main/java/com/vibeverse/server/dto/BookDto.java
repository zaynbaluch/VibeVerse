package com.vibeverse.server.dto;

import com.vibeverse.server.entity.ViberMedia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Long id;
    private String title;
    private String description;
    private String author;
    private String isbn;
    private LocalDate publishDate;
    private Set<String> genres;
    private String coverImage;
    private String externalId;
    private ViberMedia.ExternalApiType externalApi;
    private Integer pageCount;
    private String publisher;
}
