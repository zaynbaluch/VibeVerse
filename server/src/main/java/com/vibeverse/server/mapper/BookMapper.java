package com.vibeverse.server.mapper;

import com.vibeverse.server.dto.BookDto;
import com.vibeverse.server.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookMapper extends BaseMapper<Book, BookDto> {
}
