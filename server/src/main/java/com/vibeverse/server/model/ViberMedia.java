package com.vibeverse.server.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "viber_media")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViberMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "viber_media_id", updatable = false, nullable = false)
    private UUID viberMediaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viber_id", nullable = false)
    private Viber viber; // Foreign key reference to Viber

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_id", nullable = false)
    private Media media; // Foreign key reference to Media

    @Column(name = "type", nullable = false)
    private String type; // e.g., "book", "movie", "game"

    @Column(name = "rating")
    private Integer rating; // Optional rating given by the user

    @Column(name = "review")
    private String review; // Optional review text

    @Column(name = "progress")
    private Double progress; // Progress percentage (0.0 to 1.0)

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}