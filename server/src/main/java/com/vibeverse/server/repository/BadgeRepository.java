package com.vibeverse.server.repository;

import com.vibeverse.server.model.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, UUID> {
//
//    // Find by exact name (case-sensitive)
//    Optional<Badge> findByName(String name);
//
//    // Find by name containing (case-insensitive)
//    List<Badge> findByNameContainingIgnoreCase(String namePart);
//
//    // Check if name exists (for validation)
//    boolean existsByName(String name);
}