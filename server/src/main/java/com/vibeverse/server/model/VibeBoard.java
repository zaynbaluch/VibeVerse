package com.vibeverse.server.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "vibe_board")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VibeBoard {

    @Id
    @UuidGenerator
    @Column(name = "vboard_id", updatable = false, nullable = false)
    private UUID vboardId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "aurapoints", nullable = false)
    private Integer auraPoints;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viber_id", nullable = false)
    private Viber viber; // Represents the associated Viber user

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Consolidated relationship with a list of generic Media entities
    @OneToMany(mappedBy = "vibeBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default // Initialize with default empty list when using @Builder
    private List<Media> mediaItems = new ArrayList<>(); // Changed field name for clarity

    // Removed the separate lists for books, movies, and games

    // Consider adding helper methods for managing the mediaItems list
    public void addMediaItem(Media mediaItem) {
        this.mediaItems.add(mediaItem);
    }

    public void removeMediaItem(Media mediaItem) {
        this.mediaItems.remove(mediaItem);
    }
}