package com.vibeverse.server.repository;

import com.vibeverse.server.model.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, UUID> {

    // Custom finder method to check if a badge name already exists
    boolean existsByName(String name);
}