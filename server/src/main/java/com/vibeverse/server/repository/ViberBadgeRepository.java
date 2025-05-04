package com.vibeverse.server.repository;

import com.vibeverse.server.model.Badge;
import com.vibeverse.server.model.ViberBadge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ViberBadgeRepository extends JpaRepository<ViberBadge, UUID> {
    List<ViberBadge> findByViber_Id(UUID viberId);
    Optional<ViberBadge> findByIdAndViber_Id(UUID id, UUID viberId);
    boolean existsByViber_IdAndBadge_Id(UUID viberId, UUID badgeId);
    int countByViber_Id(UUID viberId);

    @Query("SELECT vb.badge FROM ViberBadge vb WHERE vb.viber.id = :viberId")
    List<Badge> findBadgesByViberId(@Param("viberId") UUID viberId);

    boolean existsByIdAndViber_Id(UUID badgeAssignmentId, UUID viberId);

}