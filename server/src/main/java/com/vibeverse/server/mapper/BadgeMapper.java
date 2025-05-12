package com.vibeverse.server.mapper;

import com.vibeverse.server.dto.BadgeDto;
import com.vibeverse.server.entity.Badge;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BadgeMapper extends BaseMapper<Badge, BadgeDto> {
    
    @Mapping(target = "viberBadges", ignore = true)
    Badge toEntity(BadgeDto dto);
}
