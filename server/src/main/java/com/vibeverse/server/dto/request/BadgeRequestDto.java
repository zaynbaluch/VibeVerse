package com.vibeverse.server.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BadgeRequestDto {

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    private String description;

    @URL
    private String iconUrl;
}
