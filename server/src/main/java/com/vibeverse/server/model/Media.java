package com.vibeverse.server.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator; // Import the new annotation
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
public class Media {

    @Id
    @UuidGenerator // Replaced @GeneratedValue and @GenericGenerator
    @Column(name = "media_id", updatable = false, nullable = false)
    private UUID mediaId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @JdbcTypeCode(SqlTypes.JSON) // Use JdbcTypeCode for JSON mapping
    @Column(name = "tags", columnDefinition = "jsonb") // Store as JSONB array in DB
    private List<String> tags; // Map to List<String> in Java

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "specific_data", columnDefinition = "jsonb")
    private Map<String, Object> specificData; // JSONB column for dynamic data

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Optional: Last updated timestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}