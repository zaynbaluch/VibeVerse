package com.vibeverse.server.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book extends BaseEntity {



    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    private String author;

    @Column(unique = true)
    private String isbn;

    private LocalDate publishDate;

    @ElementCollection
    @CollectionTable(name = "book_genres", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "genre")
    private Set<String> genres = new HashSet<>();

    private String coverImage;

    @Column(nullable = false, unique = true)
    private String externalId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ViberMedia.ExternalApiType externalApi;

    private Integer pageCount;

    private String publisher;
}
