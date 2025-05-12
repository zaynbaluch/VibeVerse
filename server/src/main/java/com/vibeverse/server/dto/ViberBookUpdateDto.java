package com.vibeverse.server.dto;

import com.vibeverse.server.entity.ViberBook;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class ViberBookUpdateDto extends ViberMediaUpdateDto {
    
    @Min(value = 0, message = "Pages read must be at least 0")
    private Integer pagesRead;
    
    private ViberBook.ReadStatus readStatus;
}
