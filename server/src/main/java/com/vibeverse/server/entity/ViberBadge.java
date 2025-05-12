package com.vibeverse.server.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "viber_badges")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViberBadge extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viber_id", nullable = false)
    private Viber viber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_id", nullable = false)
    private Badge badge;

    @Column(nullable = false)
    private LocalDateTime awardedAt;

    @PrePersist
    protected void onCreate() {
        awardedAt = LocalDateTime.now();
    }
}
