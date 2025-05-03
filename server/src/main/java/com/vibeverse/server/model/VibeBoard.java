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
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Use ID for equals/hashCode
@ToString(exclude = {"viber", "mediaItems"}) // Exclude lazy field and collection
public class VibeBoard {

    @Id
    @UuidGenerator
    @Column(name = "vboard_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include // Include ID in equals/hashCode
    private UUID vboardId;

    @Column(name = "name", nullable = false)
    @NotNull // Add validation constraint
    @Size(min = 1, max = 255) // Example size constraint
    private String name;

    @Column(name = "aurapoints", nullable = false)
    @NotNull // Add validation constraint
    @Min(value = 0) // Aura points shouldn't be negative
    private Integer auraPoints;

    @Column(name = "description", columnDefinition = "TEXT")
    @Lob // Mark as large object
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viber_id", nullable = false)
    @NotNull // Associated Viber user is required
    private Viber viber; // Represents the associated Viber user

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @NotNull // Add validation constraint
    private LocalDateTime createdAt;

    // Consolidated relationship with a list of generic Media entities
    // mappedBy="vibeBoard" requires a 'vibeBoard' field in the Media entity
    @OneToMany(mappedBy = "vibeBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default // Initialize with default empty list when using @Builder
    @NotNull // The list itself should not be null
    @Valid // Validate each Media item in the list
    private List<Media> mediaItems = new ArrayList<>();

    // Helper methods for managing the mediaItems list (update to set back-reference)
    public void addMediaItem(Media mediaItem) {
        if (mediaItem != null) {
            mediaItems.add(mediaItem);
            mediaItem.setVibeBoard(this); // Set the back-reference
        }
    }

    public void removeMediaItem(Media mediaItem) {
        if (mediaItem != null) {
            mediaItems.remove(mediaItem);
            mediaItem.setVibeBoard(null); // Remove the back-reference
        }
    }
}