package com.vibeverse.server.repository;

import com.vibeverse.server.model.Media;
import com.vibeverse.server.model.Viber;
import com.vibeverse.server.model.ViberMedia;
import com.vibeverse.server.model.enums.MediaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ViberMediaRepository extends JpaRepository<ViberMedia, UUID> {
    // Find by viber
    List<ViberMedia> findByViber_Id(UUID viberId);

    // Find by media
    List<ViberMedia> findByMedia_MediaId(UUID mediaId);

    // Find specific viber-media relationship
    List<ViberMedia> findByViber_IdAndMedia_MediaId(UUID viberId, UUID mediaId);

    // Check if relationship exists
    boolean existsByViberAndMedia(Viber viber, Media media);

    // Find by media type
    List<ViberMedia> findByType(MediaType type);

    // Find by minimum rating
    List<ViberMedia> findByRatingGreaterThanEqual(Integer minRating);

    // Find by type and minimum rating
    List<ViberMedia> findByTypeAndRatingGreaterThanEqual(MediaType type, Integer minRating);

    // Find by viber and type
    List<ViberMedia> findByViber_IdAndType(UUID viberId, MediaType type);

    // Find by media and minimum rating
    List<ViberMedia> findByMedia_MediaIdAndRatingGreaterThanEqual(UUID mediaId, Integer minRating);
}