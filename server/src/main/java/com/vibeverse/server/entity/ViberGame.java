package com.vibeverse.server.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "viber_games")
@Getter
@Setter
@NoArgsConstructor
public class ViberGame extends ViberMedia {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    private Integer hoursPlayed;

    @Enumerated(EnumType.STRING)
    private PlayStatus playStatus;

    public enum PlayStatus {
        PLAN_TO_PLAY, PLAYING, COMPLETED, ON_HOLD, DROPPED
    }

    @Builder
    public ViberGame(Viber viber, Long mediaId, String externalId, ExternalApiType externalApi, 
                    MediaType type, Integer rating, String review, Integer progress, 
                    Game game, Integer hoursPlayed, PlayStatus playStatus) {
        super(viber, mediaId, externalId, externalApi, type, rating, review, progress);
        this.game = game;
        this.hoursPlayed = hoursPlayed;
        this.playStatus = playStatus;
    }
}
