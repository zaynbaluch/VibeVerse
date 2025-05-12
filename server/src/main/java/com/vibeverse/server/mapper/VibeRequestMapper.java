package com.vibeverse.server.mapper;

import com.vibeverse.server.dto.VibeRequestCreateDto;
import com.vibeverse.server.dto.VibeRequestDto;
import com.vibeverse.server.dto.VibeRequestUpdateDto;
import com.vibeverse.server.entity.VibeRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface VibeRequestMapper extends BaseMapper<VibeRequest, VibeRequestDto> {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sender", ignore = true)
    @Mapping(target = "receiver", ignore = true)
    @Mapping(target = "sentAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    VibeRequest toEntity(VibeRequestCreateDto dto);
    
    @Mapping(target = "senderId", source = "sender.id")
    @Mapping(target = "senderUsername", source = "sender.username")
    @Mapping(target = "receiverId", source = "receiver.id")
    @Mapping(target = "receiverUsername", source = "receiver.username")
    VibeRequestDto toDto(VibeRequest entity);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sender", ignore = true)
    @Mapping(target = "receiver", ignore = true)
    @Mapping(target = "sentAt", ignore = true)
    void updateEntity(VibeRequestUpdateDto dto, @MappingTarget VibeRequest entity);
}
