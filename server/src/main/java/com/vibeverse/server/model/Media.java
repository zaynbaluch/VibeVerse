package com.vibeverse.server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull; // Import NotNull
import jakarta.validation.constraints.Size; // Import Size
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "media")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Use ID for equals/hashCode
@ToString(exclude = {"specificData", "vibeBoard"}) // Exclude potentially large JSON and lazy relationship
public class Media {

    @Id
    @UuidGenerator
    @Column(name = "media_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include // Include ID in equals/hashCode
    private UUID mediaId;

    @Column(name = "title", nullable = false, length = 255) // Added length
    @NotNull // Add validation constraint
    @Size(min = 1, max = 255) // Added Size constraint mirroring DTO
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    @Lob // Mark as large object
    @Size(max = 1000) // Added Size constraint mirroring DTO limit
    private String description;

    @Column(name = "image_url", length = 255) // Added length
    // Consider adding @URL if you use Bean Validation URL constraint
    @Size(max = 255) // Added Size constraint mirroring DTO
    private String imageUrl;

    @JdbcTypeCode(SqlTypes.JSON) // Use JdbcTypeCode for JSON mapping (Hibernate 6+)
    @Column(name = "tags", columnDefinition = "jsonb") // Store as JSONB array in DB (PostgreSQL example)
    // @NotNull // Add if tags list should never be null
    private List<String> tags; // Map to List<String> in Java

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "specific_data", columnDefinition = "jsonb") // JSONB column for dynamic data (PostgreSQL example)
    // @NotNull // Add if specificData map should never be null
    private Map<String, Object> specificData; // JSONB column for dynamic data

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @NotNull // Add validation constraint
    private LocalDateTime createdAt;

    @UpdateTimestamp // Automatically set/update timestamp on entity changes
    @Column(name = "updated_at", nullable = false) // Often 'updated_at' is non-nullable
    @NotNull // Add validation constraint
    private LocalDateTime updatedAt; // Use @UpdateTimestamp

    // Many-to-One relationship back to VibeBoard (if it exists)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vboard_id") // Foreign key column in the 'media' table
    private VibeBoard vibeBoard; // Represents the VibeBoard this media belongs to (can be null if not on a board)

    // Helper method to set the back-reference when adding to VibeBoard list
    // This should be called from VibeBoard's addMediaItem method
    public void setVibeBoard(VibeBoard vibeBoard) {
        this.vibeBoard = vibeBoard;
    }
}