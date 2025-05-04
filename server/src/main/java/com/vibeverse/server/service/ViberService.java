package com.vibeverse.server.service;

import com.vibeverse.server.dto.request.ViberRequestDto;
import com.vibeverse.server.dto.response.ViberResponseDto;
import com.vibeverse.server.exception.ResourceNotFoundException;
import com.vibeverse.server.model.Viber;
import com.vibeverse.server.repository.ViberRepository;
import com.vibeverse.server.dto.mapper.ViberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}