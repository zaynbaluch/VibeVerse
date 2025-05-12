package com.vibeverse.server.mapper;
//package com.vibeverse.mapper;

import com.vibeverse.server.dto.VibeBoardMediaDto;
import com.vibeverse.server.entity.VibeBoardMedia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface VibeBoardMediaMapper {

    @Mapping(target = "vibeBoardId", source = "vibeBoard.id")
    @Mapping(target = "vibeBoardName", source = "vibeBoard.name")
    @Mapping(target = "animeDetails", ignore = true)
    @Mapping(target = "movieDetails", ignore = true)
    @Mapping(target = "bookDetails", ignore = true)
    @Mapping(target = "gameDetails", ignore = true)
    VibeBoardMediaDto toDto(VibeBoardMedia entity);

    List<VibeBoardMediaDto> toDtoList(List<VibeBoardMedia> entities);
}