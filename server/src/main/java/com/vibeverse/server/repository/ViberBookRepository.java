package com.vibeverse.server.repository;

import com.vibeverse.server.entity.ViberBook;
import com.vibeverse.server.entity.Book;
import com.vibeverse.server.entity.Viber;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ViberBookRepository extends BaseRepository<ViberBook> {
    List<ViberBook> findByViber(Viber viber);
    List<ViberBook> findByViberId(Long viberId);
    List<ViberBook> findByBook(Book book);
    Optional<ViberBook> findByViberIdAndBookId(Long viberId, Long bookId);
}
