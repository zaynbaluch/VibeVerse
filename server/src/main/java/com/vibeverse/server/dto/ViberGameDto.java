package com.vibeverse.server.dto;

import com.vibeverse.server.entity.ViberGame;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class ViberGameDto extends ViberMediaDto {
    private GameDto game;
    private Integer hoursPlayed;
    private ViberGame.PlayStatus playStatus;
}
