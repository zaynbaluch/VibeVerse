package com.vibeverse.server.repository;

import com.vibeverse.server.entity.VibeRequest;
import com.vibeverse.server.entity.Viber;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VibeRequestRepository extends BaseRepository<VibeRequest> {
    List<VibeRequest> findBySender(Viber sender);
    List<VibeRequest> findByReceiver(Viber receiver);
    List<VibeRequest> findBySenderIdAndStatus(Long senderId, VibeRequest.RequestStatus status);
    List<VibeRequest> findByReceiverIdAndStatus(Long receiverId, VibeRequest.RequestStatus status);
    Optional<VibeRequest> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
}
