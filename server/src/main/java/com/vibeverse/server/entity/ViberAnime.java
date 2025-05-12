package com.vibeverse.server.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "viber_anime")
@Getter
@Setter
@NoArgsConstructor
public class ViberAnime extends ViberMedia {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anime_id")
    private Anime anime;

    private Integer episodesWatched;

    @Enumerated(EnumType.STRING)
    private WatchStatus watchStatus;

    public enum WatchStatus {
        PLAN_TO_WATCH, WATCHING, COMPLETED, ON_HOLD, DROPPED
    }

    @Builder
    public ViberAnime(Viber viber, Long mediaId, String externalId, ExternalApiType externalApi, 
                     MediaType type, Integer rating, String review, Integer progress, 
                     Anime anime, Integer episodesWatched, WatchStatus watchStatus) {
        super(viber, mediaId, externalId, externalApi, type, rating, review, progress);
        this.anime = anime;
        this.episodesWatched = episodesWatched;
        this.watchStatus = watchStatus;
    }
}
