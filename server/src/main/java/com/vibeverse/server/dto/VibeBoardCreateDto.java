package com.vibeverse.server.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VibeBoardCreateDto {
    
    @NotBlank(message = "Board name is required")
    @Size(min = 3, max = 100, message = "Board name must be between 3 and 100 characters")
    private String name;
    
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;


}
