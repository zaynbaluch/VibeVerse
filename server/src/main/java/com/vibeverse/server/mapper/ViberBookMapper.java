package com.vibeverse.server.mapper;

import com.vibeverse.server.dto.ViberBookDto;
import com.vibeverse.server.dto.ViberBookUpdateDto;
import com.vibeverse.server.entity.ViberBook;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {BookMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ViberBookMapper {
    
    @Mapping(target = "viberId", source = "viber.id")
    @Mapping(target = "viberUsername", source = "viber.username")
    ViberBookDto toDto(ViberBook entity);
    
    void updateEntity(ViberBookUpdateDto dto, @MappingTarget ViberBook entity);
}
