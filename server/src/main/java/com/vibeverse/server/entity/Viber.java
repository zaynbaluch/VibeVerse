package com.vibeverse.server.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vibers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Viber extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    private Integer age;

    @Column(length = 1000)
    private String bio;

    private String profilePic;

    @OneToMany(mappedBy = "viber", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VibeBoard> vibeBoards = new HashSet<>();

    @OneToMany(mappedBy = "viber", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ViberMedia> viberMedia = new HashSet<>();

    @OneToMany(mappedBy = "sender")
    private Set<VibeRequest> sentRequests = new HashSet<>();

    @OneToMany(mappedBy = "receiver")
    private Set<VibeRequest> receivedRequests = new HashSet<>();

    @OneToMany(mappedBy = "viber", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ViberBadge> badges = new HashSet<>();
}
