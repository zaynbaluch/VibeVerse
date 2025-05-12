package com.vibeverse.server.mapper;

import com.vibeverse.server.dto.ViberMovieDto;
import com.vibeverse.server.dto.ViberMovieUpdateDto;
import com.vibeverse.server.entity.ViberMovie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {MovieMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ViberMovieMapper {
    
    @Mapping(target = "viberId", source = "viber.id")
    @Mapping(target = "viberUsername", source = "viber.username")
    ViberMovieDto toDto(ViberMovie entity);
    
    void updateEntity(ViberMovieUpdateDto dto, @MappingTarget ViberMovie entity);
}
