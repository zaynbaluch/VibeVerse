package com.vibeverse.server.repository;

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

//    // Basic queries
//    List<ViberMedia> findByViber_Id(UUID viberId);
//    List<ViberMedia> findByMedia_Id(UUID mediaId);
//
//    // Type-specific queries
//    List<ViberMedia> findByType(MediaType type);
//    List<ViberMedia> findByViber_IdAndType(UUID viberId, MediaType type);
//
//    // Rating-based queries
//    List<ViberMedia> findByRatingGreaterThanEqual(int minRating);
//    List<ViberMedia> findByViber_IdAndRatingGreaterThanEqual(UUID viberId, int minRating);
//
//    // Progress tracking
//    List<ViberMedia> findByProgressGreaterThan(double minProgress);
//    List<ViberMedia> findByProgressBetween(double minProgress, double maxProgress);
//
//    // Update operations
//    @Modifying
//    @Query("UPDATE ViberMedia vm SET vm.progress = :progress WHERE vm.viberMediaId = :id")
//    void updateProgress(@Param("id") UUID id, @Param("progress") double progress);
//
//    @Modifying
//    @Query("UPDATE ViberMedia vm SET vm.rating = :rating, vm.review = :review WHERE vm.viberMediaId = :id")
//    void updateRatingAndReview(
//            @Param("id") UUID id,
//            @Param("rating") Integer rating,
//            @Param("review") String review
//    );
}