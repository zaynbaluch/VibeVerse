package com.vibeverse.server.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "anime")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Anime extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    private String studio;

    @ElementCollection
    @CollectionTable(name = "anime_tags", joinColumns = @JoinColumn(name = "anime_id"))
    @Column(name = "tag")
    private Set<String> tags = new HashSet<>();

    private LocalDate releaseDate;

    private String coverImage;

    @Column(nullable = false, unique = true)
    private String externalId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ViberMedia.ExternalApiType externalApi;

    private Integer episodes;

    @Enumerated(EnumType.STRING)
    private AnimeStatus status;

    public enum AnimeStatus {
        AIRING, FINISHED, UPCOMING
    }
}
