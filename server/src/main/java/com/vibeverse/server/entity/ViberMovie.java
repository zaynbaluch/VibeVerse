package com.vibeverse.server.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "viber_movies")
@Getter
@Setter
@NoArgsConstructor
public class ViberMovie extends ViberMedia {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Enumerated(EnumType.STRING)
    private WatchStatus watchStatus;

    public enum WatchStatus {
        PLAN_TO_WATCH, WATCHING, COMPLETED, ON_HOLD, DROPPED
    }

    @Builder
    public ViberMovie(Viber viber, Long mediaId, String externalId, ExternalApiType externalApi, 
                     MediaType type, Integer rating, String review, Integer progress, 
                     Movie movie, WatchStatus watchStatus) {
        super(viber, mediaId, externalId, externalApi, type, rating, review, progress);
        this.movie = movie;
        this.watchStatus = watchStatus;
    }
}
