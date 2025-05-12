package com.vibeverse.server.repository;

import com.vibeverse.server.entity.ViberMovie;
import com.vibeverse.server.entity.Movie;
import com.vibeverse.server.entity.Viber;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ViberMovieRepository extends BaseRepository<ViberMovie> {
    List<ViberMovie> findByViber(Viber viber);
    List<ViberMovie> findByViberId(Long viberId);
    List<ViberMovie> findByMovie(Movie movie);
    Optional<ViberMovie> findByViberIdAndMovieId(Long viberId, Long movieId);
}
