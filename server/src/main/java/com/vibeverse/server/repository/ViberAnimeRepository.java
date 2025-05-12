package com.vibeverse.server.repository;

import com.vibeverse.server.entity.ViberAnime;
import com.vibeverse.server.entity.Anime;
import com.vibeverse.server.entity.Viber;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ViberAnimeRepository extends BaseRepository<ViberAnime> {
    List<ViberAnime> findByViber(Viber viber);
    List<ViberAnime> findByViberId(Long viberId);
    List<ViberAnime> findByAnime(Anime anime);
    Optional<ViberAnime> findByViberIdAndAnimeId(Long viberId, Long animeId);
}
