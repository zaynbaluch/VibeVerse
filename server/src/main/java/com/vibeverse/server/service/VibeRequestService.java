package com.vibeverse.server.service;

import com.vibeverse.server.dto.VibeRequestCreateDto;
import com.vibeverse.server.dto.VibeRequestDto;
import com.vibeverse.server.dto.VibeRequestUpdateDto;
import com.vibeverse.server.entity.VibeRequest;
import com.vibeverse.server.entity.Viber;
import com.vibeverse.server.exception.BadRequestException;
import com.vibeverse.server.exception.ResourceNotFoundException;
import com.vibeverse.server.exception.UnauthorizedException;
import com.vibeverse.server.mapper.VibeRequestMapper;
import com.vibeverse.server.repository.VibeRequestRepository;
import com.vibeverse.server.repository.ViberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VibeRequestService {

    private final VibeRequestRepository vibeRequestRepository;
    private final ViberRepository viberRepository;
    private final VibeRequestMapper vibeRequestMapper;
    private final ViberService viberService;
    
    @Transactional(readOnly = true)
    public List<VibeRequestDto> getSentRequests() {
        Viber viber = viberService.getAuthenticatedViber();
        List<VibeRequest> requests = vibeRequestRepository.findBySender(viber);
        return vibeRequestMapper.toDtoList(requests);
    }
    
    @Transactional(readOnly = true)
    public List<VibeRequestDto> getReceivedRequests() {
        Viber viber = viberService.getAuthenticatedViber();
        List<VibeRequest> requests = vibeRequestRepository.findByReceiver(viber);
        return vibeRequestMapper.toDtoList(requests);
    }
    
    @Transactional(readOnly = true)
    public List<VibeRequestDto> getPendingReceivedRequests() {
        Viber viber = viberService.getAuthenticatedViber();
        List<VibeRequest> requests = vibeRequestRepository.findByReceiverIdAndStatus(
                viber.getId(), VibeRequest.RequestStatus.PENDING);
        return vibeRequestMapper.toDtoList(requests);
    }
    
    @Transactional
    public VibeRequestDto sendRequest(VibeRequestCreateDto vibeRequestCreateDto) {
        Viber sender = viberService.getAuthenticatedViber();
        Viber receiver = viberRepository.findById(vibeRequestCreateDto.getReceiverId())
                .orElseThrow(() -> new ResourceNotFoundException("Viber", "id", vibeRequestCreateDto.getReceiverId()));
        
        // Check if sender and receiver are the same
        if (sender.getId().equals(receiver.getId())) {
            throw new BadRequestException("You cannot send a friend request to yourself");
        }
        
        // Check if a request already exists
        Optional<VibeRequest> existingRequest = vibeRequestRepository.findBySenderIdAndReceiverId(
                sender.getId(), receiver.getId());
        
        if (existingRequest.isPresent()) {
            VibeRequest request = existingRequest.get();
            if (request.getStatus() == VibeRequest.RequestStatus.PENDING) {
                throw new BadRequestException("A pending request already exists");
            } else if (request.getStatus() == VibeRequest.RequestStatus.ACCEPTED) {
                throw new BadRequestException("You are already friends with this user");
            } else {
                // If rejected, allow to send again
                request.setStatus(VibeRequest.RequestStatus.PENDING);
                vibeRequestRepository.save(request);
                return vibeRequestMapper.toDto(request);
            }
        }
        
        // Check if there's a request in the opposite direction
        Optional<VibeRequest> oppositeRequest = vibeRequestRepository.findBySenderIdAndReceiverId(
                receiver.getId(), sender.getId());
        
        if (oppositeRequest.isPresent()) {
            VibeRequest request = oppositeRequest.get();
            if (request.getStatus() == VibeRequest.RequestStatus.PENDING) {
                // Auto-accept the opposite request
                request.setStatus(VibeRequest.RequestStatus.ACCEPTED);
                vibeRequestRepository.save(request);
                return vibeRequestMapper.toDto(request);
            } else if (request.getStatus() == VibeRequest.RequestStatus.ACCEPTED) {
                throw new BadRequestException("You are already friends with this user");
            }
        }
        
        // Create a new request
        VibeRequest vibeRequest = new VibeRequest();
        vibeRequest.setSender(sender);
        vibeRequest.setReceiver(receiver);
        vibeRequest.setStatus(VibeRequest.RequestStatus.PENDING);
        
        vibeRequestRepository.save(vibeRequest);
        
        return vibeRequestMapper.toDto(vibeRequest);
    }
    
    @Transactional
    public VibeRequestDto respondToRequest(Long requestId, VibeRequestUpdateDto vibeRequestUpdateDto) {
        VibeRequest vibeRequest = vibeRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("VibeRequest", "id", requestId));
        
        // Check if the authenticated user is the receiver
        Viber viber = viberService.getAuthenticatedViber();
        if (!vibeRequest.getReceiver().getId().equals(viber.getId())) {
            throw new UnauthorizedException("You don't have permission to respond to this request");
        }
        
        // Check if the request is pending
        if (vibeRequest.getStatus() != VibeRequest.RequestStatus.PENDING) {
            throw new BadRequestException("This request has already been processed");
        }
        
        vibeRequestMapper.updateEntity(vibeRequestUpdateDto, vibeRequest);
        vibeRequestRepository.save(vibeRequest);
        
        return vibeRequestMapper.toDto(vibeRequest);
    }
    
    @Transactional
    public void cancelRequest(Long requestId) {
        VibeRequest vibeRequest = vibeRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("VibeRequest", "id", requestId));
        
        // Check if the authenticated user is the sender
        Viber viber = viberService.getAuthenticatedViber();
        if (!vibeRequest.getSender().getId().equals(viber.getId())) {
            throw new UnauthorizedException("You don't have permission to cancel this request");
        }
        
        // Check if the request is pending
        if (vibeRequest.getStatus() != VibeRequest.RequestStatus.PENDING) {
            throw new BadRequestException("This request has already been processed");
        }
        
        vibeRequestRepository.delete(vibeRequest);
    }
}
