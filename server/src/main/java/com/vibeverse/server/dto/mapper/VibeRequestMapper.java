package com.vibeverse.server.dto.mapper;

import com.vibeverse.server.dto.request.VibeRequestRequestDto;
import com.vibeverse.server.dto.response.VibeRequestResponseDto;
import com.vibeverse.server.model.VibeRequest;
import com.vibeverse.server.model.Viber;
import com.vibeverse.server.model.enums.RequestStatus;
import com.vibeverse.server.repository.ViberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class VibeRequestMapper {

    private final ViberRepository viberRepository;

    // Convert Request DTO → Entity
    public VibeRequest toEntity(VibeRequestRequestDto dto) {
        Viber sender = viberRepository.findById(dto.getSenderId())
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));
        Viber receiver = viberRepository.findById(dto.getReceiverId())
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

        return VibeRequest.builder()
                .status(dto.getStatus())
                .message(dto.getMessage())
                .sender(sender)
                .receiver(receiver)
                .build();
    }

    // Convert Entity → Response DTO
    public VibeRequestResponseDto toResponseDto(VibeRequest entity) {
        return VibeRequestResponseDto.builder()
                .id(entity.getId())
                .status(entity.getStatus())
                .message(entity.getMessage())
                .sentAt(entity.getSentAt())
                .respondedAt(entity.getRespondedAt())
                .senderId(entity.getSender().getId())
                .receiverId(entity.getReceiver().getId())
                .build();
    }

    // Update Entity from Request DTO
    public void updateEntityFromDto(VibeRequestRequestDto dto, VibeRequest entity) {
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
            if (dto.getStatus() != RequestStatus.PENDING) {
                entity.setRespondedAt(LocalDateTime.now());
            }
        }
        if (dto.getMessage() != null) {
            entity.setMessage(dto.getMessage());
        }
    }
}