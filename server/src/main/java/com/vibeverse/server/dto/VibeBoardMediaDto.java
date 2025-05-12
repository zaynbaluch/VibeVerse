package com.vibeverse.server.dto;
//package com.vibeverse.dto;

import com.vibeverse.server.entity.ViberMedia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VibeBoardMediaDto {
    private Long id;
    private Long vibeBoardId;
    private String vibeBoardName;
    private Long mediaId;
    private ViberMedia.MediaType mediaType;
    private LocalDateTime addedAt;
    private String note;

    // Media details based on type
    private AnimeDto animeDetails;
    private MovieDto movieDetails;
    private BookDto bookDetails;
    private GameDto gameDetails;
}