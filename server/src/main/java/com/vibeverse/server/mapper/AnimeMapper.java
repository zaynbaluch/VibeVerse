package com.vibeverse.server.mapper;

import com.vibeverse.server.dto.AnimeDto;
import com.vibeverse.server.entity.Anime;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AnimeMapper extends BaseMapper<Anime, AnimeDto> {
}
