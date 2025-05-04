package com.vibeverse.server.dto.response;


import com.vibeverse.server.model.enums.RequestStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VibeRequestResponseDto {

    private UUID id;
    private RequestStatus status;
    private String message;
    private LocalDateTime sentAt;
    private LocalDateTime respondedAt;
    private UUID senderId;
    private UUID receiverId;
}