package com.vibeverse.server.dto;

import com.vibeverse.server.entity.ViberBook;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class ViberBookDto extends ViberMediaDto {
    private BookDto book;
    private Integer pagesRead;
    private ViberBook.ReadStatus readStatus;
}
