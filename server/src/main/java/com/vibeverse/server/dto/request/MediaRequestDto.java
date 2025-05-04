package com.vibeverse.server.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaRequestDto {

    @NotNull
    @Size(min = 1, max = 255)
    private String title;

    @Size(max = 1000)
    private String description;

    @URL
    @Size(max = 255)
    private String imageUrl;

    private List<String> tags;

    private Map<String, Object> specificData;
}
