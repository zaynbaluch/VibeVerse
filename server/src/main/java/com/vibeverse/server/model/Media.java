package com.vibeverse.server.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator; // Import the new annotation
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
    @UuidGenerator // Replaced @GeneratedValue and @GenericGenerator
    @Column(name = "media_id", updatable = false, nullable = false)
    private UUID mediaId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    // The use of TEXT[] for tags is database-specific and not a deprecated Hibernate/JPA feature,
    // but consider if a more structured approach like a separate entity or a JSONB array
    // (using @JdbcTypeCode(SqlTypes.JSON) as for specificData) would be more suitable
    // depending on your querying and data structure needs.
    @Column(name = "tags", columnDefinition = "TEXT[]")
    private String tags; // Array of tags (stored as TEXT[])

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