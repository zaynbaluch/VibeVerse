package com.vibeverse.server.repository;

import com.vibeverse.server.model.VibeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VibeBoardRepository extends JpaRepository<VibeBoard, Long> {
}
