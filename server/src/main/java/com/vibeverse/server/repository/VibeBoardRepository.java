package com.vibeverse.server.repository;

import com.vibeverse.server.model.VibeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VibeBoardRepository extends JpaRepository<VibeBoard, UUID> {

//    // Basic queries
//    Optional<VibeBoard> findByName(String name);
//    List<VibeBoard> findByNameContainingIgnoreCase(String namePart);
//    boolean existsByName(String name);
//
//    // Find by Viber (owner)
//    List<VibeBoard> findByViber_Id(UUID viberId);
//
//    // Find boards with minimum aura points
//    List<VibeBoard> findByAuraPointsGreaterThanEqual(int minPoints);
//
//    // Search in description (full-text)
//    @Query("SELECT v FROM VibeBoard v WHERE LOWER(v.description) LIKE LOWER(concat('%', :query, '%'))")
//    List<VibeBoard> searchInDescription(@Param("query") String query);
//
//    // Count media items in a board
//    @Query("SELECT SIZE(v.mediaItems) FROM VibeBoard v WHERE v.vboardId = :boardId")
//    int countMediaItems(@Param("boardId") UUID boardId);
}