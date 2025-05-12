package com.vibeverse.server.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VibeRequestCreateDto {
    
    @NotNull(message = "Receiver ID is required")
    private Long receiverId;
}
