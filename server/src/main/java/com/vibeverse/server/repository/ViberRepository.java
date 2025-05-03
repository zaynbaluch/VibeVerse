package com.vibeverse.server.repository;

import com.vibeverse.server.model.Viber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViberRepository extends JpaRepository<Viber, Long> {
    // add custom queries here if needed
}
