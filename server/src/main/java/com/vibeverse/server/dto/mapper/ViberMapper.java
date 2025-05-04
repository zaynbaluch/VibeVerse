package com.vibeverse.server.dto.mapper;

import com.vibeverse.server.dto.request.ViberRequestDto;
import com.vibeverse.server.dto.response.ViberResponseDto;
import com.vibeverse.server.model.Viber;
import org.springframework.stereotype.Component;

@Component
public class ViberMapper {

    // Convert Request DTO → Entity (for creation)
    public Viber toEntity(ViberRequestDto dto) {
        return Viber.builder()
                .username(dto.getUsername())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(dto.getPassword()) // Note: Password should be encoded before saving
                .bio(dto.getBio())
                .profilePictureUrl(dto.getProfilePictureUrl())
                .dateOfBirth(dto.getDateOfBirth())
                .build();
    }

    // Convert Entity → Response DTO (excludes sensitive/relationship data)
    public ViberResponseDto toResponseDto(Viber entity) {
        return ViberResponseDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .bio(entity.getBio())
                .profilePictureUrl(entity.getProfilePictureUrl())
                .dateOfBirth(entity.getDateOfBirth())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    // Update Entity from Request DTO (null-safe)
    public void updateEntityFromDto(ViberRequestDto dto, Viber entity) {
        if (dto.getUsername() != null) {
            entity.setUsername(dto.getUsername());
        }
        if (dto.getFirstName() != null) {
            entity.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            entity.setLastName(dto.getLastName());
        }
        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }
        if (dto.getPassword() != null) {
            entity.setPassword(dto.getPassword()); // Remember to encode!
        }
        if (dto.getBio() != null) {
            entity.setBio(dto.getBio());
        }
        if (dto.getProfilePictureUrl() != null) {
            entity.setProfilePictureUrl(dto.getProfilePictureUrl());
        }
        if (dto.getDateOfBirth() != null) {
            entity.setDateOfBirth(dto.getDateOfBirth());
        }
    }
}