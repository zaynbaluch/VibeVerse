package com.vibeverse.server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*; // Keep all validation imports
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList; // Import ArrayList
import java.util.List; // Import List
import java.util.Set; // Consider Set for relationships where order doesn't matter and uniqueness within the collection is key
import java.util.HashSet; // Import HashSet

@Entity
@Table(name = "vibers") // Explicitly define table name, good practice
@Getter // Use explicit Getter
@Setter // Use explicit Setter
@NoArgsConstructor
@AllArgsConstructor // Use explicit AllArgsConstructor
@Builder // Use explicit Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Use ID for equals/hashCode
@ToString(exclude = {"password", "viberMedia", "vibeBoards", "viberBadges", "sentRequests", "receivedRequests"}) // Exclude sensitive fields and lazy collections
public class Viber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Using Long with IDENTITY
    @Column(name = "id", updatable = false, nullable = false) // Explicit column definition
    @EqualsAndHashCode.Include // Include ID in equals/hashCode
    private Long id; // Primary key type is Long

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    @Column(nullable = false, unique = true, length = 20) // Explicit length matching Size
    private String username;

    @NotBlank(message = "First name is required")
    @Size(max = 100) // Add max size constraint
    @Column(name = "first_name", nullable = false, length = 100) // Explicit length
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100) // Add max size constraint
    @Column(name = "last_name", nullable = false, length = 100) // Explicit length
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 255) // Add max size constraint
    @Column(nullable = false, unique = true, length = 255) // Explicit length
    private String email;

    @NotBlank(message = "Password is required")
    // Note: This field should store the *hashed* password, not the plain text password.
    // Implement password hashing in your service layer before saving.
    @Column(nullable = false) // Length depends on hashing algorithm output, usually 60-80 chars for bcrypt
    private String password; // Stores the hashed password

    @Size(max = 500, message = "Bio cannot exceed 500 characters")
    @Column(length = 500) // Explicit length matching Size
    private String bio;

    @Column(name = "profile_picture_url")
    // Consider adding @URL if you use Bean Validation URL constraint
    @Size(max = 255) // Add max size constraint for URLs
    private String profilePictureUrl;

    @Past(message = "Date of birth must be in the past")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false) // Explicitly nullable = false
    @NotNull // Add validation constraint
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false) // Explicitly nullable = false
    @NotNull // Add validation constraint
    private LocalDateTime updatedAt;

    // --- Relationships (One-to-Many) ---

    // A Viber can have many ViberMedia entries in their collection
    @OneToMany(mappedBy = "viber", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default // Initialize with default empty list when using @Builder
    // @NotNull // Collection itself is not null, individual items might be validated via @Valid
    // @Valid // Validate elements in the collection - useful if ViberMedia had validation rules
    private List<ViberMedia> viberMedia = new ArrayList<>();

    // A Viber can own many VibeBoards
    @OneToMany(mappedBy = "viber", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<VibeBoard> vibeBoards = new ArrayList<>();

    // A Viber can be awarded many ViberBadge instances
    // Consider Set if a Viber can only earn a specific badge once, and order doesn't matter.
    // Using List allows for earning the same badge multiple times.
    @OneToMany(mappedBy = "viber", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<ViberBadge> viberBadges = new ArrayList<>();

    // A Viber can send many VibeRequests
    // No cascade=ALL or orphanRemoval=true here usually, as deleting a user might not delete requests
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<VibeRequest> sentRequests = new HashSet<>(); // Using Set as request order doesn't matter

    // A Viber can receive many VibeRequests
    // No cascade=ALL or orphanRemoval=true here usually
    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<VibeRequest> receivedRequests = new HashSet<>(); // Using Set

    // --- Helper methods for managing collections (important for bidirectional relationships) ---

    public void addViberMedia(ViberMedia media) {
        if (media != null) {
            viberMedia.add(media);
            media.setViber(this); // Set the many-to-one side
        }
    }

    public void removeViberMedia(ViberMedia media) {
        if (media != null) {
            viberMedia.remove(media);
            media.setViber(null); // Unset the many-to-one side
        }
    }

    // Add similar helper methods for vibeBoards, viberBadges, sentRequests, receivedRequests
    // Example for VibeBoard:
    public void addVibeBoard(VibeBoard board) {
        if (board != null) {
            vibeBoards.add(board);
            board.setViber(this); // Set the many-to-one side
        }
    }

    public void removeVibeBoard(VibeBoard board) {
        if (board != null) {
            vibeBoards.remove(board);
            board.setViber(null); // Unset the many-to-one side
        }
    }

    // ... add methods for viberBadges, sentRequests, receivedRequests ...
}