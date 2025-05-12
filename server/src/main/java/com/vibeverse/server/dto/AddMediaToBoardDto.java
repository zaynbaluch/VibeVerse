package com.vibeverse.server.dto;
//package com.vibeverse.dto;

import com.vibeverse.server.entity.ViberMedia;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddMediaToBoardDto {

    @NotNull(message = "Media ID is required")
    private Long mediaId;

    @NotNull(message = "Media type is required")
    private ViberMedia.MediaType mediaType;

    @Size(max = 500, message = "Note cannot exceed 500 characters")
    private String note;
}