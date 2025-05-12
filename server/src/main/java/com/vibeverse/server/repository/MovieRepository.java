package com.vibeverse.server.repository;

import com.vibeverse.server.entity.Movie;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends BaseRepository<Movie> {
    Optional<Movie> findByExternalId(String externalId);
}
