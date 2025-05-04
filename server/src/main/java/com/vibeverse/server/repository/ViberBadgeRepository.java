package com.vibeverse.server.repository;

import com.vibeverse.server.model.ViberBadge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ViberBadgeRepository extends JpaRepository<ViberBadge, UUID> {

}