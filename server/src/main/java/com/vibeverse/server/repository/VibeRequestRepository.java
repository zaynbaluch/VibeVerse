package com.vibeverse.server.repository;

import com.vibeverse.server.model.VibeRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VibeRequestRepository extends JpaRepository<VibeRequest, Long> {
}
