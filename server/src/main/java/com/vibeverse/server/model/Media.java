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
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"specificData"})
public class Media {

    @Id
    @UuidGenerator
    @Column(name = "media_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    private UUID mediaId;

    @Column(name = "title", nullable = false, length = 255) // Added length
    @NotNull
    @Size(min = 1, max = 255)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    @Lob
    @Size(max = 1000)
    private String description;

    @Column(name = "image_url", length = 255)
    @URL
    @Size(max = 255)
    private String imageUrl;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "tags", columnDefinition = "jsonb")
    private List<String> tags;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "specific_data", columnDefinition = "jsonb")
    private Map<String, Object> specificData;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @NotNull
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    @NotNull
    private LocalDateTime updatedAt;

    // FOREIGN REFERENCES

    @OneToMany(mappedBy = "media", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ViberMedia> viberMedia = new ArrayList<>();

    // METHODS

    public void addViberMedia(ViberMedia vm) {
        viberMedia.add(vm);
        vm.setMedia(this);
    }

    public void removeViberMedia(ViberMedia vm) {
        viberMedia.remove(vm);
        vm.setMedia(null);
    }


    public void clearViberMedia() {
        viberMedia.clear();
    }

    public boolean hasViberMedia(ViberMedia vm) {
        return viberMedia.contains(vm);
    }

}