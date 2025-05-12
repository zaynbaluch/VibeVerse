package com.vibeverse.server.mapper;

import com.vibeverse.server.dto.RegisterRequestDto;
import com.vibeverse.server.dto.ViberDto;
import com.vibeverse.server.dto.ViberUpdateDto;
import com.vibeverse.server.entity.Viber;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ViberMapper extends BaseMapper<Viber, ViberDto> {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vibeBoards", ignore = true)
    @Mapping(target = "viberMedia", ignore = true)
    @Mapping(target = "sentRequests", ignore = true)
    @Mapping(target = "receivedRequests", ignore = true)
    @Mapping(target = "badges", ignore = true)
    @Mapping(target = "passwordHash", source = "password")
    Viber toEntity(RegisterRequestDto dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "vibeBoards", ignore = true)
    @Mapping(target = "viberMedia", ignore = true)
    @Mapping(target = "sentRequests", ignore = true)
    @Mapping(target = "receivedRequests", ignore = true)
    @Mapping(target = "badges", ignore = true)
    void updateEntity(ViberUpdateDto dto, @MappingTarget Viber entity);
}
