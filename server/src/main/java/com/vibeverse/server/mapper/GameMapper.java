package com.vibeverse.server.mapper;

import com.vibeverse.server.dto.GameDto;
import com.vibeverse.server.entity.Game;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GameMapper extends BaseMapper<Game, GameDto> {
}
