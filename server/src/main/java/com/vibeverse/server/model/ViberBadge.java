package com.vibeverse.server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator; // Import UuidGenerator

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "viber_badges")
// Note: If a Viber can only have a badge once, add a unique constraint like this:
// @Table(name = "viber_badges", uniqueConstraints = @UniqueConstraint(columnNames = {"viber_id", "badge_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Use ID for equals/hashCode
@ToString(exclude = {"viber", "badge"}) // Exclude lazy fields
public class ViberBadge {

    @Id
    @UuidGenerator // Use @UuidGenerator for consistency
    @Column(name = "viber_badge_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include // Include ID in equals/hashCode
    private UUID viberBadgeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viber_id", nullable = false)
    @NotNull // Associated Viber is required
    private Viber viber; // Foreign key reference to Viber

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_id", nullable = false)
    @NotNull // Associated Badge is required
    private Badge badge; // Foreign key reference to Badge

    @CreationTimestamp // Records when this specific ViberBadge instance was created (awarded)
    @Column(name = "awarded_at", nullable = false, updatable = false)
    @NotNull // Add validation constraint
    private LocalDateTime awardedAt;

    // No need for @UpdateTimestamp here as this entity typically represents a single event (being awarded)
    // If you needed to track modifications to *this* record (e.g., notes, status), you would add it.
}