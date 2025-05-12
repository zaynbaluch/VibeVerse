package com.vibeverse.server.dto;

import com.vibeverse.server.entity.ViberAnime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class ViberAnimeDto extends ViberMediaDto {
    private AnimeDto anime;
    private Integer episodesWatched;
    private ViberAnime.WatchStatus watchStatus;
}
