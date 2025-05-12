package com.vibeverse.server.dto;

import com.vibeverse.server.entity.VibeRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VibeRequestUpdateDto {
    
    @NotNull(message = "Status is required")
    private VibeRequest.RequestStatus status;
}
