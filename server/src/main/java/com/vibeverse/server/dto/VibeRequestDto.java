package com.vibeverse.server.dto;

import com.vibeverse.server.entity.VibeRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VibeRequestDto {
    private Long id;
    private Long senderId;
    private String senderUsername;
    private Long receiverId;
    private String receiverUsername;
    private LocalDateTime sentAt;
    private VibeRequest.RequestStatus status;
}
