package com.vibeverse.server.mapper;

import com.vibeverse.server.dto.VibeBoardCreateDto;
import com.vibeverse.server.dto.VibeBoardDto;
import com.vibeverse.server.dto.VibeBoardUpdateDto;
import com.vibeverse.server.entity.VibeBoard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface VibeBoardMapper extends BaseMapper<VibeBoard, VibeBoardDto> {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "viber", ignore = true)
    VibeBoard toEntity(VibeBoardCreateDto dto);
    
    @Mapping(target = "viberId", source = "viber.id")
    @Mapping(target = "viberUsername", source = "viber.username")
    VibeBoardDto toDto(VibeBoard entity);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "viber", ignore = true)
    void updateEntity(VibeBoardUpdateDto dto, @MappingTarget VibeBoard entity);
}
