package com.vibeverse.server.repository;

import com.vibeverse.server.entity.ViberBadge;
import com.vibeverse.server.entity.Viber;
import com.vibeverse.server.entity.Badge;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ViberBadgeRepository extends BaseRepository<ViberBadge> {
    List<ViberBadge> findByViber(Viber viber);
    List<ViberBadge> findByViberId(Long viberId);
    List<ViberBadge> findByBadge(Badge badge);
    Optional<ViberBadge> findByViberIdAndBadgeId(Long viberId, Long badgeId);
}
