package com.vibeverse.server.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "vibe_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VibeRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "reciever_id", nullable = false)
    private String recieverId;

    @CreationTimestamp
    @Column(name = "sent_at", nullable = false, updatable = false)
    private LocalDateTime sentAt;

    @Column(name = "sender_id", nullable = false)
    private String senderId;

    // You can add additional fields and relationships here as needed
}