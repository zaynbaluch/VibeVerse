package com.vibeverse.server.repository;

import com.vibeverse.server.entity.Anime;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnimeRepository extends BaseRepository<Anime> {
    Optional<Anime> findByExternalId(String externalId);
}
