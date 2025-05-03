package com.vibeverse.server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min; // Import Min
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size; // Import Size
import jakarta.validation.Valid; // Import Valid for validating list elements
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"viber", "mediaItems"})
public class VibeBoard {

    @Id
    @UuidGenerator
    @Column(name = "vboard_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    private UUID vboardId;

    @Column(name = "name", nullable = false)
    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @Column(name = "aurapoints", nullable = false)
    @NotNull
    @Min(value = 0)
    private Integer auraPoints;

    @Column(name = "description", columnDefinition = "TEXT")
    @Lob
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @NotNull
    private LocalDateTime createdAt;

    // FOREIGN REFERENCES

    @OneToMany
    @Builder.Default
    @NotNull
    @Valid
    private List<Media> mediaItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viber_id", nullable = false)
    @NotNull
    private Viber viber;

    // METHODS

    public void addMediaItem(Media media) {
        if (mediaItems == null) {
            mediaItems = new ArrayList<>();
        }
        mediaItems.add(media);
    }

    public void removeMediaItem(Media media) {
        if (mediaItems != null) {
            mediaItems.remove(media);
        }
    }

    public void assignViber(Viber viber) {
        if (viber == null) {
            throw new IllegalArgumentException("Viber cannot be null");
        }
        this.viber = viber;
    }

}