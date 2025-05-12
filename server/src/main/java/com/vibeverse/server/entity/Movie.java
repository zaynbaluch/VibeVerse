package com.vibeverse.server.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    private String director;

    @ElementCollection
    @CollectionTable(name = "movie_cast", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "actor")
    private Set<String> cast = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "movie_genres", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "genre")
    private Set<String> genres = new HashSet<>();

    private LocalDate releaseDate;

    private String coverImage;

    @Column(nullable = false, unique = true)
    private String externalId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ViberMedia.ExternalApiType externalApi;

    private Integer runtime;

    @Enumerated(EnumType.STRING)
    private MediaType type;

    public enum MediaType {
        MOVIE, SERIES
    }
}
