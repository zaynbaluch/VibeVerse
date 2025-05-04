package com.vibeverse.server.repository;

import com.vibeverse.server.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MediaRepository extends JpaRepository<Media, UUID> {
//
//    // Basic queries
//    Optional<Media> findByTitle(String title);
//    List<Media> findByTitleContainingIgnoreCase(String titlePart);
//    boolean existsByTitle(String title);
//
//    // JSON array contains query (for tags)
//    @Query("SELECT m FROM Media m WHERE :tag MEMBER OF m.tags")
//    List<Media> findByTag(@Param("tag") String tag);

//    // JSON property query (for specificData)
//    @Query(value = """
//        SELECT * FROM media
//        WHERE specific_data @> CAST(:jsonQuery AS jsonb)
//        """, nativeQuery = true)
//    List<Media> findBySpecificDataProperty(@Param("jsonQuery") String jsonQuery);
//
//    // Full-text search on title and description
//    @Query("""
//        SELECT m FROM Media m
//        WHERE to_tsvector('english', m.title || ' ' || m.description)
//        @@ to_tsquery('english', :query)
//        """)
//    List<Media> fullTextSearch(@Param("query") String query);
}