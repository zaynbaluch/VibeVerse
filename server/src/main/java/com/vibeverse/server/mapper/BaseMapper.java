package com.vibeverse.server.mapper;

import com.vibeverse.server.entity.BaseEntity;
import org.mapstruct.MappingTarget;

import java.util.List;

public interface BaseMapper<E extends BaseEntity, D> {
    D toDto(E entity);
    List<D> toDtoList(List<E> entities);
    E toEntity(D dto);
    void updateEntity(D dto, @MappingTarget E entity);
}
