package com.vibeverse.server.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "vboard_id", updatable = false, nullable = false)
    private UUID vboardId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "aurapoints", nullable = false)
    private Integer auraPoints;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "viber_id", nullable = false)
    private UUID viberId; // assuming it references another table's UUID

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Relationships (assuming Book, Movie, Game entities exist with FK to vboard_id)

//    @Transient
//    private List<Book> books;
//
//    @Transient
//    private List<Movie> movies;
//
//    @Transient
//    private List<Game> games;
}