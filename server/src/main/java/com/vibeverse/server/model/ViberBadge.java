package com.vibeverse.server.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator; // Import Hibernate's UuidGenerator

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "viber_badges")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViberBadge {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "viber_badge_id", updatable = false, nullable = false)
    private UUID viberBadgeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viber_id", nullable = false)
    private Viber viber; // Foreign key reference to Viber

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_id", nullable = false)
    private Badge badge; // Foreign key reference to Badge

    @CreationTimestamp
    @Column(name = "awarded_at", nullable = false, updatable = false)
    private LocalDateTime awardedAt;
}