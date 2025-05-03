package com.vibeverse.server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size; // Import Size
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator; // Import UuidGenerator

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "badges")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Use ID for equals/hashCode
@ToString // Default toString is fine as no lazy fields or large LOBs are included
public class Badge {

    @Id
    @UuidGenerator // Use @UuidGenerator for consistency
    @Column(name = "badge_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include // Include ID in equals/hashCode
    private UUID badgeId;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    @NotNull // Add validation constraint
    @Size(min = 1, max = 100) // Add size constraint
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    @Lob // Mark as large object
    private String description;

    @Column(name = "icon_url")
    // Consider adding @URL if you use Bean Validation URL constraint
    private String iconUrl;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @NotNull // Add validation constraint
    private LocalDateTime createdAt;

    @UpdateTimestamp // Automatically set/update timestamp on entity changes
    @Column(name = "updated_at", nullable = false) // Often 'updated_at' is non-nullable
    @NotNull // Add validation constraint
    private LocalDateTime updatedAt; // Use @UpdateTimestamp
}