package com.vibeverse.server.repository;

import com.vibeverse.server.model.ViberMedia;
import com.vibeverse.server.model.enums.MediaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ViberMediaRepository extends JpaRepository<ViberMedia, UUID> {

}