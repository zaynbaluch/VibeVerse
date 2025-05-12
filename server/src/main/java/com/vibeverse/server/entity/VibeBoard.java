//package com.vibeverse.server.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Table(name = "vibe_boards")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class VibeBoard extends BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String name;
//
//    @Column(length = 1000)
//    private String description;
//
//    @Column(nullable = false)
//    private LocalDateTime createdAt;
//
//    @ManyToMany
//    @JoinTable(
//            name = "vibe_board_anime",
//            joinColumns = @JoinColumn(name = "vibe_board_id"),
//            inverseJoinColumns = @JoinColumn(name = "anime_id")
//    )
//    private List<Anime> animeList = new ArrayList<>();
//
//    @ManyToMany
//    @JoinTable(
//            name = "vibe_board_movies",
//            joinColumns = @JoinColumn(name = "vibe_board_id"),
//            inverseJoinColumns = @JoinColumn(name = "movie_id")
//    )
//    private List<Movie> movieList = new ArrayList<>();
//
//    @ManyToMany
//    @JoinTable(
//            name = "vibe_board_books",
//            joinColumns = @JoinColumn(name = "vibe_board_id"),
//            inverseJoinColumns = @JoinColumn(name = "book_id")
//    )
//    private List<Book> bookList = new ArrayList<>();
//
//    @ManyToMany
//    @JoinTable(
//            name = "vibe_board_games",
//            joinColumns = @JoinColumn(name = "vibe_board_id"),
//            inverseJoinColumns = @JoinColumn(name = "game_id")
//    )
//    private List<Game> gameList = new ArrayList<>();
//
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "viber_id", nullable = false)
//    private Viber viber;
//
//    @PrePersist
//    protected void onCreate() {
//        createdAt = LocalDateTime.now();
//    }
//
//
//
//}

package com.vibeverse.server.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vibe_boards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VibeBoard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viber_id", nullable = false)
    private Viber viber;

    @OneToMany(mappedBy = "vibeBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VibeBoardMedia> mediaItems = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Helper method to add media to the board
    public void addMedia(VibeBoardMedia media) {
        mediaItems.add(media);
        media.setVibeBoard(this);
    }

    // Helper method to remove media from the board
    public void removeMedia(VibeBoardMedia media) {
        mediaItems.remove(media);
        media.setVibeBoard(null);
    }
}