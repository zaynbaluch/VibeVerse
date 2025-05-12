package com.vibeverse.server.repository;

import com.vibeverse.server.entity.Badge;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BadgeRepository extends BaseRepository<Badge> {
    Optional<Badge> findByName(String name);
}
