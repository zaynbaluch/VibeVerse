package com.vibeverse.server.dto;

import com.vibeverse.server.entity.ViberAnime;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class ViberAnimeUpdateDto extends ViberMediaUpdateDto {
    
    @Min(value = 0, message = "Episodes watched must be at least 0")
    private Integer episodesWatched;
    
    private ViberAnime.WatchStatus watchStatus;
}
