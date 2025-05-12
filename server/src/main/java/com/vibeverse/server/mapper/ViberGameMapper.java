package com.vibeverse.server.mapper;

import com.vibeverse.server.dto.ViberGameDto;
import com.vibeverse.server.dto.ViberGameUpdateDto;
import com.vibeverse.server.entity.ViberGame;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {GameMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ViberGameMapper {
    
    @Mapping(target = "viberId", source = "viber.id")
    @Mapping(target = "viberUsername", source = "viber.username")
    ViberGameDto toDto(ViberGame entity);
    
    void updateEntity(ViberGameUpdateDto dto, @MappingTarget ViberGame entity);
}
