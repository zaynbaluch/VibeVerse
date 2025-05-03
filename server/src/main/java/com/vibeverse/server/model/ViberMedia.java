package com.vibeverse.server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

// Define the enum for media type outside the class or in its own file
enum MediaType {
    BOOK,
    MOVIE,
    GAME,
    // Add other types as needed
}

@Entity
@Table(name = "viber_media")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // Good for creating instances, especially in tests or factories
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Use ID for equals/hashCode
@ToString(exclude = {"viber", "media"}) // Exclude lazy fields from toString
public class ViberMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "viber_media_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include // Include ID in equals/hashCode
    private UUID viberMediaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viber_id", nullable = false)
    @NotNull // Add validation constraint
    private Viber viber; // Foreign key reference to Viber

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_id", nullable = false)
    @NotNull // Add validation constraint
    private Media media; // Foreign key reference to Media

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING) // Map enum to String in DB
    @NotNull // Add validation constraint
    private MediaType type; // e.g., BOOK, MOVIE, GAME

    @Column(name = "rating")
    @Min(value = 1) // Assuming rating is 1-5 or 1-10, adjust min/max accordingly
    @Max(value = 10) // Example: max rating is 10
    private Integer rating; // Optional rating given by the user

    @Column(name = "review", columnDefinition = "TEXT") // Use TEXT for potentially longer reviews
    private String review; // Optional review text

    @Column(name = "progress")
    @Min(value = 0) // Progress should be between 0.0 and 1.0
    @Max(value = 1)
    private Double progress; // Progress percentage (0.0 to 1.0)

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp // Automatically set/update timestamp on entity changes
    @Column(name = "updated_at", nullable = false) // Often 'updated_at' is non-nullable
    private LocalDateTime updatedAt;

    // --- You might add convenience methods here if needed ---
    // Example:
    // public boolean isCompleted() {
    //     return progress != null && progress >= 1.0;
    // }
}