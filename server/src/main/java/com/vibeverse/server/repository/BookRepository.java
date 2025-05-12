package com.vibeverse.server.repository;

import com.vibeverse.server.entity.Book;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends BaseRepository<Book> {
    Optional<Book> findByExternalId(String externalId);
    Optional<Book> findByIsbn(String isbn);
}
