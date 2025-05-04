package com.vibeverse.server.repository;

import com.vibeverse.server.model.ViberBadge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ViberBadgeRepository extends JpaRepository<ViberBadge, UUID> {

//    // Basic queries
//    List<ViberBadge> findByViber_Id(Long viberId);
//    List<ViberBadge> findByBadge_Id(UUID badgeId);
//
//    // Find by award date range
//    List<ViberBadge> findByAwardedAtBetween(LocalDateTime start, LocalDateTime end);
//
//    // Check if a viber has a specific badge
//    boolean existsByViber_IdAndBadge_Id(Long viberId, UUID badgeId);
//
//    // Count badges per viber
//    @Query("SELECT COUNT(vb) FROM ViberBadge vb WHERE vb.viber.id = :viberId")
//    int countByViber(@Param("viberId") Long viberId);
//
//    // Find latest awarded badges
//    @Query("SELECT vb FROM ViberBadge vb ORDER BY vb.awardedAt DESC LIMIT :limit")
//    List<ViberBadge> findRecentBadges(@Param("limit") int limit);
}