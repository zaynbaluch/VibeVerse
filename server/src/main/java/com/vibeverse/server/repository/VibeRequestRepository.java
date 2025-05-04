package com.vibeverse.server.repository;

import com.vibeverse.server.model.VibeRequest;
import com.vibeverse.server.model.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface VibeRequestRepository extends JpaRepository<VibeRequest, UUID> {

//    // Basic queries
//    List<VibeRequest> findBySender_Id(UUID senderId);
//    List<VibeRequest> findByReceiver_Id(UUID receiverId);
//
//    // Status-based queries
//    List<VibeRequest> findByStatus(RequestStatus status);
//    List<VibeRequest> findBySender_IdAndStatus(UUID senderId, RequestStatus status);
//    List<VibeRequest> findByReceiver_IdAndStatus(UUID receiverId, RequestStatus status);
//
//    // Date-based queries
//    List<VibeRequest> findBySentAtBetween(LocalDateTime start, LocalDateTime end);
//    List<VibeRequest> findByRespondedAtBetween(LocalDateTime start, LocalDateTime end);
//
//    // Count pending requests
//    @Query("SELECT COUNT(vr) FROM VibeRequest vr WHERE vr.receiver.id = :receiverId AND vr.status = 'PENDING'")
//    long countPendingRequests(@Param("receiverId") UUID receiverId);
//
//    // Update status
//    @Modifying
//    @Query("UPDATE VibeRequest vr SET vr.status = :status, vr.respondedAt = CURRENT_TIMESTAMP WHERE vr.id = :id")
//    void updateRequestStatus(@Param("id") UUID id, @Param("status") RequestStatus status);
}