package com.vibeverse.server.service;

import com.vibeverse.server.dto.request.ViberRequestDto;
import com.vibeverse.server.dto.response.ViberResponseDto;
import com.vibeverse.server.exception.ResourceNotFoundException;
import com.vibeverse.server.model.Viber;
import com.vibeverse.server.model.enums.RequestStatus;
import com.vibeverse.server.repository.ViberRepository;
import com.vibeverse.server.dto.mapper.ViberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vibeverse.server.model.enums.MediaType;

//package com.vibeverse.server.service;

import com.vibeverse.server.dto.request.*;
import com.vibeverse.server.dto.response.*;
import com.vibeverse.server.exception.ResourceNotFoundException;
import com.vibeverse.server.model.*;
import com.vibeverse.server.repository.*;
import com.vibeverse.server.dto.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ViberService {

    private final ViberRepository viberRepository;
    private final ViberMapper viberMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ViberResponseDto createViber(ViberRequestDto dto) {
        if (viberRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (viberRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        Viber viber = viberMapper.toEntity(dto);
        viber.setPassword(passwordEncoder.encode(dto.getPassword()));
        return viberMapper.toResponseDto(viberRepository.save(viber));
    }

    @Transactional(readOnly = true)
    public ViberResponseDto getViberById(UUID id) {
        Viber viber = viberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Viber not found with id: " + id));
        return viberMapper.toResponseDto(viber);
    }

    @Transactional(readOnly = true)
    public List<ViberResponseDto> getAllVibers() {
        return viberRepository.findAll().stream()
                .map(viberMapper::toResponseDto)
                .toList();
    }

    @Transactional
    public ViberResponseDto updateViber(UUID id, ViberRequestDto dto) {
        Viber viber = viberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Viber not found with id: " + id));

        if (dto.getUsername() != null && !dto.getUsername().equals(viber.getUsername())
                && viberRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Username already taken");
        }

        if (dto.getEmail() != null && !dto.getEmail().equals(viber.getEmail())
                && viberRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        viberMapper.updateEntityFromDto(dto, viber);
        if (dto.getPassword() != null) {
            viber.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return viberMapper.toResponseDto(viberRepository.save(viber));
    }

    @Transactional
    public void deleteViber(UUID id) {
        if (!viberRepository.existsById(id)) {
            throw new ResourceNotFoundException("Viber not found with id: " + id);
        }
        viberRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public ViberResponseDto getViberByUsername(String username) {
        Viber viber = viberRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Viber not found with username: " + username));
        return viberMapper.toResponseDto(viber);
    }

    @Transactional(readOnly = true)
    public List<ViberResponseDto> searchVibers(String query) {
        return viberRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query)
                .stream()
                .map(viberMapper::toResponseDto)
                .toList();
    }





    // Existing dependencies
//    private final ViberRepository viberRepository;
//    private final ViberMapper viberMapper;
//    private final PasswordEncoder passwordEncoder;

    // New dependencies for VibeBoard operations
    private final VibeBoardRepository vibeBoardRepository;
    private final VibeBoardMapper vibeBoardMapper;
    private final MediaRepository mediaRepository;

    // Existing Viber CRUD methods...

    /* ========== VIBE BOARD OPERATIONS ========== */

    @Transactional
    public VibeBoardResponseDto createVibeBoard(UUID viberId, VibeBoardRequestDto dto) {
        Viber viber = viberRepository.findById(viberId)
                .orElseThrow(() -> new ResourceNotFoundException("Viber not found"));

        if (vibeBoardRepository.existsByNameAndViber(dto.getName(), viber)) {
            throw new IllegalArgumentException("You already have a board with this name");
        }

        dto.setViberId(viberId); // Ensure the board belongs to this viber
        VibeBoard board = vibeBoardMapper.toEntity(dto);
        return vibeBoardMapper.toResponseDto(vibeBoardRepository.save(board));
    }

    @Transactional(readOnly = true)
    public List<VibeBoardResponseDto> getVibeBoards(UUID viberId) {
        if (!viberRepository.existsById(viberId)) {
            throw new ResourceNotFoundException("Viber not found");
        }
        return vibeBoardRepository.findByViber_Id(viberId).stream()
                .map(vibeBoardMapper::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public VibeBoardResponseDto getVibeBoard(UUID viberId, UUID boardId) {
        VibeBoard board = vibeBoardRepository.findByIdAndViber_Id(boardId, viberId)
                .orElseThrow(() -> new ResourceNotFoundException("Board not found"));
        return vibeBoardMapper.toResponseDto(board);
    }

    @Transactional
    public VibeBoardResponseDto updateVibeBoard(UUID viberId, UUID boardId, VibeBoardRequestDto dto) {
        VibeBoard board = vibeBoardRepository.findByIdAndViber_Id(boardId, viberId)
                .orElseThrow(() -> new ResourceNotFoundException("Board not found"));

        if (dto.getName() != null && !dto.getName().equals(board.getName())
                && vibeBoardRepository.existsByNameAndViber(dto.getName(), board.getViber())) {
            throw new IllegalArgumentException("You already have a board with this name");
        }

        dto.setViberId(viberId); // Prevent changing owner
        vibeBoardMapper.updateEntityFromDto(dto, board);
        return vibeBoardMapper.toResponseDto(vibeBoardRepository.save(board));
    }

    @Transactional
    public void deleteVibeBoard(UUID viberId, UUID boardId) {
        if (!vibeBoardRepository.existsByIdAndViber_Id(boardId, viberId)) {
            throw new ResourceNotFoundException("Board not found");
        }
        vibeBoardRepository.deleteById(boardId);
    }

    @Transactional
    public VibeBoardResponseDto addMediaToBoard(UUID viberId, UUID boardId, UUID mediaId) {
        VibeBoard board = vibeBoardRepository.findByIdAndViber_Id(boardId, viberId)
                .orElseThrow(() -> new ResourceNotFoundException("Board not found"));

        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new ResourceNotFoundException("Media not found"));

        if (!board.getMediaItems().contains(media)) {
            board.addMediaItem(media);
        }

        return vibeBoardMapper.toResponseDto(vibeBoardRepository.save(board));
    }

    @Transactional
    public VibeBoardResponseDto removeMediaFromBoard(UUID viberId, UUID boardId, UUID mediaId) {
        VibeBoard board = vibeBoardRepository.findByIdAndViber_Id(boardId, viberId)
                .orElseThrow(() -> new ResourceNotFoundException("Board not found"));

        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new ResourceNotFoundException("Media not found"));

        board.removeMediaItem(media);
        return vibeBoardMapper.toResponseDto(vibeBoardRepository.save(board));
    }

    @Transactional
    public VibeBoardResponseDto updateBoardAuraPoints(UUID viberId, UUID boardId, int points) {
        VibeBoard board = vibeBoardRepository.findByIdAndViber_Id(boardId, viberId)
                .orElseThrow(() -> new ResourceNotFoundException("Board not found"));

        board.setAuraPoints(board.getAuraPoints() + points);
        return vibeBoardMapper.toResponseDto(vibeBoardRepository.save(board));
    }


    private final ViberBadgeRepository viberBadgeRepository;
    private final ViberBadgeMapper viberBadgeMapper;

    // Add these to your existing ViberService class
    @Transactional(readOnly = true)
    public List<ViberBadgeResponseDto> getViberBadges(UUID viberId) {
        if (!viberRepository.existsById(viberId)) {
            throw new ResourceNotFoundException("Viber not found");
        }
        return viberBadgeRepository.findByViber_Id(viberId).stream()
                .map(viberBadgeMapper::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ViberBadgeResponseDto getViberBadge(UUID viberId, UUID badgeAssignmentId) {
        return viberBadgeMapper.toResponseDto(
                viberBadgeRepository.findByIdAndViber_Id(badgeAssignmentId, viberId)
                        .orElseThrow(() -> new ResourceNotFoundException("Badge assignment not found"))
        );
    }

    @Transactional
    public ViberBadgeResponseDto awardBadgeToViber(UUID viberId, ViberBadgeRequestDto dto) {
        // Verify the viber matches the path
        if (!viberId.equals(dto.getViberId())) {
            throw new IllegalArgumentException("Viber ID mismatch");
        }

        // Check if badge is already awarded
        if (viberBadgeRepository.existsByViber_IdAndBadge_Id(viberId, dto.getBadgeId())) {
            throw new IllegalArgumentException("Viber already has this badge");
        }

        ViberBadge assignment = viberBadgeMapper.toEntity(dto);
        return viberBadgeMapper.toResponseDto(viberBadgeRepository.save(assignment));
    }

    @Transactional
    public void removeBadgeFromViber(UUID viberId, UUID badgeAssignmentId) {
        if (!viberBadgeRepository.existsByIdAndViber_Id(badgeAssignmentId, viberId)) {
            throw new ResourceNotFoundException("Badge assignment not found");
        }
        viberBadgeRepository.deleteById(badgeAssignmentId);
    }

    @Transactional(readOnly = true)
    public boolean hasBadge(UUID viberId, UUID badgeId) {
        return viberBadgeRepository.existsByViber_IdAndBadge_Id(viberId, badgeId);
    }

    @Transactional(readOnly = true)
    public int getViberBadgeCount(UUID viberId) {
        return viberBadgeRepository.countByViber_Id(viberId);
    }


    // Add this dependency to the service
    private final VibeRequestRepository vibeRequestRepository;
    private final VibeRequestMapper vibeRequestMapper;

    /* ========== VIBE REQUEST OPERATIONS ========== */

    @Transactional
    public VibeRequestResponseDto createVibeRequest(VibeRequestRequestDto dto) {
        // Check if request already exists
        if (vibeRequestRepository.existsBySender_IdAndReceiver_Id(dto.getSenderId(), dto.getReceiverId())) {
            throw new IllegalArgumentException("Request already exists between these users");
        }

        // Prevent self-requests
        if (dto.getSenderId().equals(dto.getReceiverId())) {
            throw new IllegalArgumentException("Cannot send request to yourself");
        }

        VibeRequest request = vibeRequestMapper.toEntity(dto);
        return vibeRequestMapper.toResponseDto(vibeRequestRepository.save(request));
    }

    @Transactional(readOnly = true)
    public List<VibeRequestResponseDto> getSentRequests(UUID viberId) {
        return vibeRequestRepository.findBySender_Id(viberId).stream()
                .map(vibeRequestMapper::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<VibeRequestResponseDto> getReceivedRequests(UUID viberId) {
        return vibeRequestRepository.findByReceiver_Id(viberId).stream()
                .map(vibeRequestMapper::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public VibeRequestResponseDto getVibeRequestById(UUID requestId) {
        return vibeRequestMapper.toResponseDto(
                vibeRequestRepository.findById(requestId)
                        .orElseThrow(() -> new ResourceNotFoundException("Request not found"))
        );
    }

    @Transactional
    public VibeRequestResponseDto updateVibeRequest(UUID requestId, VibeRequestRequestDto dto) {
        VibeRequest request = vibeRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));

        // Only allow updating status and message
        if (dto.getSenderId() != null && !dto.getSenderId().equals(request.getSender().getId())) {
            throw new IllegalArgumentException("Cannot change request sender");
        }

        if (dto.getReceiverId() != null && !dto.getReceiverId().equals(request.getReceiver().getId())) {
            throw new IllegalArgumentException("Cannot change request receiver");
        }

        vibeRequestMapper.updateEntityFromDto(dto, request);
        return vibeRequestMapper.toResponseDto(vibeRequestRepository.save(request));
    }

    @Transactional
    public void deleteVibeRequest(UUID requestId) {
        if (!vibeRequestRepository.existsById(requestId)) {
            throw new ResourceNotFoundException("Request not found");
        }
        vibeRequestRepository.deleteById(requestId);
    }

    @Transactional(readOnly = true)
    public List<VibeRequestResponseDto> getRequestsByStatus(UUID viberId, RequestStatus status) {
        return vibeRequestRepository.findByReceiver_IdAndStatus(viberId, status).stream()
                .map(vibeRequestMapper::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public boolean requestExists(UUID senderId, UUID receiverId) {
        return vibeRequestRepository.existsBySender_IdAndReceiver_Id(senderId, receiverId);
    }


    // Add these dependencies to the service
    private final ViberMediaRepository viberMediaRepository;
//    private final MediaRepository mediaRepository;
    private final com.vibeverse.server.mapper.ViberMediaMapper viberMediaMapper;

    /* ========== VIBER MEDIA OPERATIONS ========== */

    @Transactional
    public ViberMediaResponseDto createViberMedia(ViberMediaRequestDto dto) {
        Viber viber = viberRepository.findById(dto.getViberId())
                .orElseThrow(() -> new ResourceNotFoundException("Viber not found"));

        Media media = mediaRepository.findById(dto.getMediaId())
                .orElseThrow(() -> new ResourceNotFoundException("Media not found"));

        // Check if this viber-media relationship already exists
        if (viberMediaRepository.existsByViberAndMedia(viber, media)) {
            throw new IllegalArgumentException("This viber already has a relationship with this media");
        }

        ViberMedia viberMedia = viberMediaMapper.toEntity(dto, viber, media);
        return viberMediaMapper.toResponseDto(viberMediaRepository.save(viberMedia));
    }

    @Transactional(readOnly = true)
    public ViberMediaResponseDto getViberMediaById(UUID viberMediaId) {
        return viberMediaMapper.toResponseDto(
                viberMediaRepository.findById(viberMediaId)
                        .orElseThrow(() -> new ResourceNotFoundException("ViberMedia not found"))
        );
    }

    @Transactional(readOnly = true)
    public List<ViberMediaResponseDto> getMediaByViberId(UUID viberId) {
        return viberMediaRepository.findByViber_Id(viberId).stream()
                .map(viberMediaMapper::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ViberMediaResponseDto> getVibersByMediaId(UUID mediaId) {
        return viberMediaRepository.findByMedia_MediaId(mediaId).stream()
                .map(viberMediaMapper::toResponseDto)
                .toList();
    }

    @Transactional
    public ViberMediaResponseDto updateViberMedia(UUID viberMediaId, ViberMediaRequestDto dto) {
        ViberMedia viberMedia = viberMediaRepository.findById(viberMediaId)
                .orElseThrow(() -> new ResourceNotFoundException("ViberMedia not found"));

        // Only update allowed fields (not viber or media references)
        if (dto.getType() != null) {
            viberMedia.setType(dto.getType());
        }
        if (dto.getRating() != null) {
            viberMedia.setRating(dto.getRating());
        }
        if (dto.getReview() != null) {
            viberMedia.setReview(dto.getReview());
        }
        if (dto.getProgress() != null) {
            viberMedia.setProgress(dto.getProgress());
        }

        return viberMediaMapper.toResponseDto(viberMediaRepository.save(viberMedia));
    }

    @Transactional
    public void deleteViberMedia(UUID viberMediaId) {
        if (!viberMediaRepository.existsById(viberMediaId)) {
            throw new ResourceNotFoundException("ViberMedia not found");
        }
        viberMediaRepository.deleteById(viberMediaId);
    }

    @Transactional(readOnly = true)
    public List<ViberMediaResponseDto> searchViberMedia(
            UUID viberId,
            UUID mediaId,
            MediaType type,
            Integer minRating
    ) {
        // Build dynamic query based on provided parameters
        if (viberId != null && mediaId != null) {
            return viberMediaRepository.findByViber_IdAndMedia_MediaId(viberId, mediaId).stream()
                    .map(viberMediaMapper::toResponseDto)
                    .toList();
        } else if (viberId != null) {
            return viberMediaRepository.findByViber_Id(viberId).stream()
                    .map(viberMediaMapper::toResponseDto)
                    .toList();
        } else if (mediaId != null) {
            return viberMediaRepository.findByMedia_MediaId(mediaId).stream()
                    .map(viberMediaMapper::toResponseDto)
                    .toList();
        } else if (type != null && minRating != null) {
            return viberMediaRepository.findByTypeAndRatingGreaterThanEqual(type, minRating).stream()
                    .map(viberMediaMapper::toResponseDto)
                    .toList();
        } else if (type != null) {
            return viberMediaRepository.findByType(type).stream()
                    .map(viberMediaMapper::toResponseDto)
                    .toList();
        } else if (minRating != null) {
            return viberMediaRepository.findByRatingGreaterThanEqual(minRating).stream()
                    .map(viberMediaMapper::toResponseDto)
                    .toList();
        } else {
            return viberMediaRepository.findAll().stream()
                    .map(viberMediaMapper::toResponseDto)
                    .toList();
        }
    }
}