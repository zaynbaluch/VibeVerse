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

    // Basic queries
    boolean existsByTitle(String title);

    List<Media> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT m FROM Media m WHERE m.imageUrl IS NOT NULL")
    List<Media> findByImageUrlIsNotNull();

    @Query("SELECT m FROM Media m WHERE :tag MEMBER OF m.tags")
    List<Media> findByTag(@Param("tag") String tag);

    @Query(value = """
        SELECT * FROM media 
        WHERE specific_data @> CAST(:jsonQuery AS jsonb)
        """, nativeQuery = true)
    List<Media> findBySpecificDataProperty(@Param("jsonQuery") String jsonQuery);
}