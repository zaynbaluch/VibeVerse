package com.vibeverse.server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "vibers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"password", "viberMedia", "vibeBoards", "viberBadges", "sentRequests", "receivedRequests"})
public class Viber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    @Column(nullable = false, unique = true, length = 20)
    private String username;

    @NotBlank(message = "First name is required")
    @Size(max = 100)
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100)
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 255)
    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @NotBlank(message = "Password is required")
    @Column(nullable = false)
    private String password;

    @Size(max = 500, message = "Bio cannot exceed 500 characters")
    @Column(length = 500)
    private String bio;

    @Size(max = 255)
    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    @Past(message = "Date of birth must be in the past")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    @NotNull
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    @NotNull
    private LocalDateTime updatedAt;

    // FOREIGN REFERENCES

    @OneToMany(mappedBy = "viber", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<ViberMedia> viberMedia = new ArrayList<>();

    @OneToMany(mappedBy = "viber", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<VibeBoard> vibeBoards = new ArrayList<>();

    @OneToMany(mappedBy = "viber", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<ViberBadge> viberBadges = new ArrayList<>();

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<VibeRequest> sentRequests = new HashSet<>();

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<VibeRequest> receivedRequests = new HashSet<>();

    // METHODS

    public void addViberMedia(ViberMedia media) {
        viberMedia.add(media);
        media.setViber(this);
    }

    public void removeViberMedia(ViberMedia media) {
        viberMedia.remove(media);
        media.setViber(null);
    }

    public void addVibeBoard(VibeBoard board) {
        vibeBoards.add(board);
        board.setViber(this);
    }

    public void removeVibeBoard(VibeBoard board) {
        vibeBoards.remove(board);
        board.setViber(null);
    }

    public void addViberBadge(ViberBadge badge) {
        viberBadges.add(badge);
        badge.setViber(this);
    }

    public void removeViberBadge(ViberBadge badge) {
        viberBadges.remove(badge);
        badge.setViber(null);
    }

    public void sendRequest(VibeRequest request) {
        sentRequests.add(request);
        request.setSender(this);
    }

    public void receiveRequest(VibeRequest request) {
        receivedRequests.add(request);
        request.setReceiver(this);
    }

    public void removeSentRequest(VibeRequest request) {
        sentRequests.remove(request);
        request.setSender(null);
    }

    public void removeReceivedRequest(VibeRequest request) {
        receivedRequests.remove(request);
        request.setReceiver(null);
    }

    public boolean hasProfilePicture() {
        return profilePictureUrl != null && !profilePictureUrl.isEmpty();
    }

    public void updateBio(String newBio) {
        if (newBio != null && newBio.length() <= 500) {
            this.bio = newBio;
        } else {
            throw new IllegalArgumentException("Bio must be less than or equal to 500 characters.");
        }
    }

    public boolean hasCompleteProfile() {
        return username != null && firstName != null && lastName != null && email != null;
    }
}
