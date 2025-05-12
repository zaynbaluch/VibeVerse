package com.vibeverse.server.repository;

import com.vibeverse.server.entity.Game;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends BaseRepository<Game> {
    Optional<Game> findByExternalId(String externalId);
}
