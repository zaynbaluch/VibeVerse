package com.vibeverse.server.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "viber_books")
@Getter
@Setter
@NoArgsConstructor
public class ViberBook extends ViberMedia {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private Integer pagesRead;

    @Enumerated(EnumType.STRING)
    private ReadStatus readStatus;

    public enum ReadStatus {
        PLAN_TO_READ, READING, COMPLETED, ON_HOLD, DROPPED
    }

    @Builder
    public ViberBook(Viber viber, Long mediaId, String externalId, ExternalApiType externalApi, 
                    MediaType type, Integer rating, String review, Integer progress, 
                    Book book, Integer pagesRead, ReadStatus readStatus) {
        super(viber, mediaId, externalId, externalApi, type, rating, review, progress);
        this.book = book;
        this.pagesRead = pagesRead;
        this.readStatus = readStatus;
    }
}
