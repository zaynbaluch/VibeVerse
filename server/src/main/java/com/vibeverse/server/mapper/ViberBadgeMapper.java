package com.vibeverse.server.mapper;

import com.vibeverse.server.dto.ViberBadgeDto;
import com.vibeverse.server.entity.ViberBadge;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ViberBadgeMapper extends BaseMapper<ViberBadge, ViberBadgeDto> {
    
    @Mapping(target = "viberId", source = "viber.id")
    @Mapping(target = "viberUsername", source = "viber.username")
    @Mapping(target = "badgeId", source = "badge.id")
    @Mapping(target = "badgeName", source = "badge.name")
    @Mapping(target = "badgeDescription", source = "badge.description")
    @Mapping(target = "badgeImageUrl", source = "badge.imageUrl")
    ViberBadgeDto toDto(ViberBadge entity);
}
