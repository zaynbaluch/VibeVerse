package com.vibeverse.server.dto;

import com.vibeverse.server.entity.ViberMedia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ViberMediaDto {
    private Long id;
    private Long viberId;
    private String viberUsername;
    private Long mediaId;
    private String externalId;
    private ViberMedia.ExternalApiType externalApi;
    private ViberMedia.MediaType type;
    private Integer rating;
    private String review;
    private Integer progress;
}
