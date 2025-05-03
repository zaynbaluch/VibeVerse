package com.vibeverse.server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

// Define the enum for request status, ideally in its own file
enum RequestStatus {
    PENDING,
    ACCEPTED,
    REJECTED,
    CANCELLED
}

// Assuming the Viber entity exists and has a UUID id field:
/*
@Entity
@Table(name = "vibers") // Or whatever your Viber table name is
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Viber { // Your User entity is called Viber
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id; // Assuming Viber uses UUID for its primary key

    // Other Viber fields...
}
*/


@Entity
@Table(name = "vibe_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Use ID for equals/hashCode
@ToString(exclude = {"sender", "receiver"}) // Exclude lazy fields from toString
public class VibeRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include // Include ID in equals/hashCode
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY) // Requests have one sender Viber
    @JoinColumn(name = "sender_id", nullable = false) // This column stores the sender's Viber ID
    @NotNull // Sender Viber is required
    private Viber sender; // Foreign key reference to the Sender Viber entity

    @ManyToOne(fetch = FetchType.LAZY) // Requests have one receiver Viber
    @JoinColumn(name = "receiver_id", nullable = false) // This column stores the receiver's Viber ID
    @NotNull // Receiver Viber is required
    private Viber receiver; // Foreign key reference to the Receiver Viber entity

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING) // Store enum as String in DB
    @NotNull // Status is required
    private RequestStatus status = RequestStatus.PENDING; // Default status is PENDING

    @Column(name = "message") // Optional message with the request
    @Lob // Use @Lob for potentially long text (CLOB/TEXT type in DB)
    private String message;

    @CreationTimestamp
    @Column(name = "sent_at", nullable = false, updatable = false)
    private LocalDateTime sentAt;

    @Column(name = "responded_at") // Timestamp for when the request was accepted/rejected/cancelled
    private LocalDateTime respondedAt; // Nullable as it's only set when the status changes

    // --- You might add convenience methods here if needed ---
    // Example:
    // public boolean isPending() {
    //     return this.status == RequestStatus.PENDING;
    // }
    // public void accept() {
    //     if (this.status == RequestStatus.PENDING) {
    //         this.status = RequestStatus.ACCEPTED;
    //         this.respondedAt = LocalDateTime.now();
    //     } // else throw exception or log warning
    // }
}