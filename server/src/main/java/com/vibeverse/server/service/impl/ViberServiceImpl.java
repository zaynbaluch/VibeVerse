package com.vibeverse.server.service.impl;

import com.vibeverse.server.dto.request.ViberRequestDto;
import com.vibeverse.server.dto.response.ViberResponseDto;
import com.vibeverse.server.model.Viber;
import com.vibeverse.server.repository.ViberRepository;
import com.vibeverse.server.service.ViberService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ViberServiceImpl implements ViberService {

    private final ViberRepository repo;

    @Override
    public ViberResponseDto createViber(ViberRequestDto req) {
        Viber v = Viber.builder()
                .username(req.getUsername())
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .email(req.getEmail())
                .password(req.getPassword())
                .bio(req.getBio())
                .profilePictureUrl(req.getProfilePictureUrl())
                .dateOfBirth(req.getDateOfBirth())
                .build();
        Viber saved = repo.save(v);
        return toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ViberResponseDto> getAllVibers() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ViberResponseDto getViberById(Long id) {
        Viber v = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Viber not found: " + id));
        return toDto(v);
    }

    @Override
    public ViberResponseDto updateViber(Long id, ViberRequestDto req) {
        Viber v = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Viber not found: " + id));
        v.setUsername(req.getUsername());
        v.setFirstName(req.getFirstName());
        v.setLastName(req.getLastName());
        v.setEmail(req.getEmail());
        v.setPassword(req.getPassword());
        v.setBio(req.getBio());
        v.setProfilePictureUrl(req.getProfilePictureUrl());
        v.setDateOfBirth(req.getDateOfBirth());
        Viber updated = repo.save(v);
        return toDto(updated);
    }

    @Override
    public void deleteViber(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Viber not found: " + id);
        }
        repo.deleteById(id);
    }

    private ViberResponseDto toDto(Viber v) {
        return ViberResponseDto.builder()
                .id(v.getId())
                .username(v.getUsername())
                .firstName(v.getFirstName())
                .lastName(v.getLastName())
                .email(v.getEmail())
                .bio(v.getBio())
                .profilePictureUrl(v.getProfilePictureUrl())
                .dateOfBirth(v.getDateOfBirth())
                .createdAt(v.getCreatedAt())
                .updatedAt(v.getUpdatedAt())
                .build();
    }
}
