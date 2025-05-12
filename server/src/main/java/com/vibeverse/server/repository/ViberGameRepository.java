package com.vibeverse.server.repository;

import com.vibeverse.server.entity.ViberGame;
import com.vibeverse.server.entity.Game;
import com.vibeverse.server.entity.Viber;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ViberGameRepository extends BaseRepository<ViberGame> {
    List<ViberGame> findByViber(Viber viber);
    List<ViberGame> findByViberId(Long viberId);
    List<ViberGame> findByGame(Game game);
    Optional<ViberGame> findByViberIdAndGameId(Long viberId, Long gameId);
}
