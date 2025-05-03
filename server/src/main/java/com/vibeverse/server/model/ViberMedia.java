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


enum MediaType {
    BOOK,
    MOVIE,
    GAME,
    SERIES,
    ANIME
}

@Entity
@Table(name = "viber_media")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class ViberMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "viber_media_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    private UUID viberMediaId;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private MediaType type;

    @Column(name = "rating")
    @Min(value = 1)
    @Max(value = 10)
    private Integer rating;

    @Column(name = "review", columnDefinition = "TEXT")
    private String review;

    @Column(name = "progress")
    @Min(value = 0)
    @Max(value = 1)
    private Double progress;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // FOREIGN REFERENCES

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viber_id", nullable = false)
    @NotNull
    private Viber viber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_id", nullable = false)
    @NotNull
    private Media media;

}