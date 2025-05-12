package com.vibeverse.server.mapper;

import com.vibeverse.server.dto.ViberAnimeDto;
import com.vibeverse.server.dto.ViberAnimeUpdateDto;
import com.vibeverse.server.entity.ViberAnime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {AnimeMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ViberAnimeMapper {
    
    @Mapping(target = "viberId", source = "viber.id")
    @Mapping(target = "viberUsername", source = "viber.username")
    ViberAnimeDto toDto(ViberAnime entity);
    
    void updateEntity(ViberAnimeUpdateDto dto, @MappingTarget ViberAnime entity);
}
