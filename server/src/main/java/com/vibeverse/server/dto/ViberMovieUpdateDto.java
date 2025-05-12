package com.vibeverse.server.dto;

import com.vibeverse.server.entity.ViberMovie;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class ViberMovieUpdateDto extends ViberMediaUpdateDto {
    private ViberMovie.WatchStatus watchStatus;
}
