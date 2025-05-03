package com.vibeverse.server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.Setter;
import lombok.Getter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator; // Import UuidGenerator

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "viber_badges")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"viber", "badge"})
public class ViberBadge {

    @Id
    @UuidGenerator
    @Column(name = "viber_badge_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    private UUID viberBadgeId;

    @CreationTimestamp
    @Column(name = "awarded_at", nullable = false, updatable = false)
    @NotNull
    private LocalDateTime awardedAt;

    //FOREIGN REFERENCES

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viber_id", nullable = false)
    @NotNull
    @Getter
    @Setter
    private Viber viber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_id", nullable = false)
    @NotNull
    @Getter
    @Setter
    private Badge badge;


}