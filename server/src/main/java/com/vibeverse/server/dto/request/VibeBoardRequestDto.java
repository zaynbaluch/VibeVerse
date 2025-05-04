package com.vibeverse.server.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
//import org.hibernate.validator.constraints.UUID;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VibeBoardRequestDto {

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @NotNull
    @Min(0)
    private Integer auraPoints;

    private String description;

    @NotNull
    private List<UUID> mediaItemIds;

    @NotNull
    private UUID viberId;
}
