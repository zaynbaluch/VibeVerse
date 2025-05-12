package com.vibeverse.server.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ViberMediaUpdateDto {
    
    @Min(value = 0, message = "Rating must be at least 0")
    @Max(value = 10, message = "Rating cannot exceed 10")
    private Integer rating;
    
    @Size(max = 2000, message = "Review cannot exceed 2000 characters")
    private String review;
    
    @Min(value = 0, message = "Progress must be at least 0")
    private Integer progress;
}
