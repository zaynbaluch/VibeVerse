package com.vibeverse.server.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "vibe_board_media")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VibeBoardMedia extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vibe_board_id", nullable = false)
    private VibeBoard vibeBoard;

    @Column(nullable = false)
    private Long mediaId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ViberMedia.MediaType mediaType;

    @Column(nullable = false)
    private LocalDateTime addedAt;

    // Optional note/comment about this media in the board
    @Column(length = 500)
    private String note;

    @PrePersist
    protected void onCreate() {
        addedAt = LocalDateTime.now();
    }
}