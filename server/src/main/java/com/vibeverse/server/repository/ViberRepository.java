package com.vibeverse.server.repository;

import com.vibeverse.server.entity.Viber;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ViberRepository extends BaseRepository<Viber> {
    Optional<Viber> findByUsername(String username);
    Optional<Viber> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
