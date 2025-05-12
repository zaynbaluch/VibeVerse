package com.vibeverse.server.mapper;

import com.vibeverse.server.dto.ViberMediaDto;
import com.vibeverse.server.dto.ViberMediaUpdateDto;
import com.vibeverse.server.entity.ViberMedia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ViberMediaMapper {
    
    @Mapping(target = "viberId", source = "viber.id")
    @Mapping(target = "viberUsername", source = "viber.username")
    ViberMediaDto toDto(ViberMedia entity);
    
    void updateEntity(ViberMediaUpdateDto dto, @MappingTarget ViberMedia entity);
}
