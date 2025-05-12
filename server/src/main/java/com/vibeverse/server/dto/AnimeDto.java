package com.vibeverse.server.dto;

import com.vibeverse.server.entity.Anime;
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
public class AnimeDto {
    private Long id;
    private String title;
    private String description;
    private String studio;
    private Set<String> tags;
    private LocalDate releaseDate;
    private String coverImage;
    private String externalId;
    private ViberMedia.ExternalApiType externalApi;
    private Integer episodes;
    private Anime.AnimeStatus status;
}
