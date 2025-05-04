package com.vibeverse.server.repository;

import com.vibeverse.server.model.VibeBoard;
import com.vibeverse.server.model.Viber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VibeBoardRepository extends JpaRepository<VibeBoard, UUID> {

    // Check if a board with the given name already exists for a specific Viber
    boolean existsByNameAndViber(String name, Viber viber);

    // Find all boards owned by a specific Viber
    List<VibeBoard> findByViber_Id(UUID viberId);

    // Find a specific board by its ID and the Viber's ID (to verify ownership)
    Optional<VibeBoard> findByIdAndViber_Id(UUID boardId, UUID viberId);

    // Check if a board with given ID exists and belongs to a Viber
    boolean existsByIdAndViber_Id(UUID boardId, UUID viberId);

}