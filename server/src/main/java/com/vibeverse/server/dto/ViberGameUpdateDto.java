package com.vibeverse.server.dto;

import com.vibeverse.server.entity.ViberGame;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class ViberGameUpdateDto extends ViberMediaUpdateDto {
    
    @Min(value = 0, message = "Hours played must be at least 0")
    private Integer hoursPlayed;
    
    private ViberGame.PlayStatus playStatus;
}
