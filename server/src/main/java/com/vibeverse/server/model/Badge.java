package com.vibeverse.server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size; // Import Size
import lombok.Setter;
import lombok.Getter;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator; // Import UuidGenerator
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "badges")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Badge {

    @Id
    @UuidGenerator
    @Column(name = "badge_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    private UUID badgeId;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    @Lob // Mark as large object
    private String description;

    @Column(name = "icon_url")
    @URL
    private String iconUrl;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @NotNull
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    @NotNull
    private LocalDateTime updatedAt;

    // FOREIGN REFERENCES

    @OneToMany(mappedBy = "badge", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ViberBadge> viberBadges = new ArrayList<>();


    // METHODS
    public void addViberBadge(ViberBadge viberBadge) {
        viberBadges.add(viberBadge);
        viberBadge.setBadge(this);
    }

    public void removeViberBadge(ViberBadge viberBadge) {
        viberBadges.remove(viberBadge);
        viberBadge.setBadge(null);
    }

}