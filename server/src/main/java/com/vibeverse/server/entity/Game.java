package com.vibeverse.server.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "games")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    private String developer;

    @ElementCollection
    @CollectionTable(name = "game_platforms", joinColumns = @JoinColumn(name = "game_id"))
    @Column(name = "platform")
    private Set<String> platforms = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "game_genres", joinColumns = @JoinColumn(name = "game_id"))
    @Column(name = "genre")
    private Set<String> genres = new HashSet<>();

    private LocalDate releaseDate;

    private String coverImage;

    @Column(nullable = false, unique = true)
    private String externalId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ViberMedia.ExternalApiType externalApi;

    private Double rating;
}
