package com.vibeverse.server.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
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
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "media_id", updatable = false, nullable = false)
    private UUID mediaId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "tags", columnDefinition = "TEXT[]")
    private String[] tags; // Array of tags

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