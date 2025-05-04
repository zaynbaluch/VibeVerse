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

    // Find all requests sent by a specific viber
    List<VibeRequest> findBySender_Id(UUID senderId);

    // Find all requests received by a specific viber
    List<VibeRequest> findByReceiver_Id(UUID receiverId);

    // Find all requests between two specific vibers (in both directions)
    List<VibeRequest> findBySender_IdOrReceiver_Id(UUID viberId1, UUID viberId2);

    // Check if a request exists between two specific vibers (sender → receiver)
    boolean existsBySender_IdAndReceiver_Id(UUID senderId, UUID receiverId);

    // Find requests by receiver and status (for filtering pending/accepted/rejected)
    List<VibeRequest> findByReceiver_IdAndStatus(UUID receiverId, RequestStatus status);

    // Find a specific request between two vibers
    List<VibeRequest> findBySender_IdAndReceiver_Id(UUID senderId, UUID receiverId);

    // Find requests by status only
    List<VibeRequest> findByStatus(RequestStatus status);

    // Count pending requests for a receiver
    long countByReceiver_IdAndStatus(UUID receiverId, RequestStatus status);
}