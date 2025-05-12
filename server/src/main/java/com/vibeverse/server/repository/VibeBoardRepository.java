package com.vibeverse.server.repository;

import com.vibeverse.server.entity.VibeBoard;
import com.vibeverse.server.entity.Viber;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VibeBoardRepository extends BaseRepository<VibeBoard> {
    List<VibeBoard> findByViber(Viber viber);
    List<VibeBoard> findByViberId(Long viberId);
}
