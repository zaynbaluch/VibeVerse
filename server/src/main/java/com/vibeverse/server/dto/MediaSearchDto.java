package com.vibeverse.server.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaSearchDto {
    
    @NotBlank(message = "Query is required")
    private String query;
    
    private Integer page;
    private Integer size;
}
