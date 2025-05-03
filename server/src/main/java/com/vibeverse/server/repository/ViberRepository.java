package com.vibeverse.server.repository;

import com.vibeverse.server.model.Viber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViberRepository extends JpaRepository<Viber, Long> {
}
