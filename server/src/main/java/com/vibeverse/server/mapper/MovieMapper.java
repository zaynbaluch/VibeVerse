package com.vibeverse.server.mapper;

import com.vibeverse.server.dto.MovieDto;
import com.vibeverse.server.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MovieMapper extends BaseMapper<Movie, MovieDto> {
}
