package com.vibeverse.server.dto;

import com.vibeverse.server.entity.Movie;
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
public class MovieDto {
    private Long id;
    private String title;
    private String description;
    private String director;
    private Set<String> cast;
    private Set<String> genres;
    private LocalDate releaseDate;
    private String coverImage;
    private String externalId;
    private ViberMedia.ExternalApiType externalApi;
    private Integer runtime;
    private Movie.MediaType type;
}
