package com.vibeverse.server.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "viber_media")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class ViberMedia extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viber_id", nullable = false)
    private Viber viber;

    @Column(nullable = false)
    private Long mediaId;

    @Column(nullable = false)
    private String externalId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ExternalApiType externalApi;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MediaType type;

    private Integer rating;

    @Column(length = 2000)
    private String review;

    private Integer progress;

    public enum ExternalApiType {
        JIKAN, TMDB, RAWG, GOOGLE_BOOKS
    }

    public enum MediaType {
        ANIME, MOVIE, SERIES, BOOK, GAME
    }
}
