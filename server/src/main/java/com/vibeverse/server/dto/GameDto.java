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
public class GameDto {
    private Long id;
    private String title;
    private String description;
    private String developer;
    private Set<String> platforms;
    private Set<String> genres;
    private LocalDate releaseDate;
    private String coverImage;
    private String externalId;
    private ViberMedia.ExternalApiType externalApi;
    private Double rating;
}
