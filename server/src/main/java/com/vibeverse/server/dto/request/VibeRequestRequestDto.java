package com.vibeverse.server.dto.request;

import com.vibeverse.server.model.VibeRequest;
import com.vibeverse.server.model.enums.RequestStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VibeRequestRequestDto {

    @NotNull(message = "Status is required")
    private RequestStatus status;

    private String message;

    @NotNull(message = "Sender ID is required")
    private UUID senderId;

    @NotNull(message = "Receiver ID is required")
    private UUID receiverId;
}