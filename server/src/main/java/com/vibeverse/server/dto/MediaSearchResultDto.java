package com.vibeverse.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaSearchResultDto<T> {
    private List<T> results;
    private int page;
    private int totalPages;
    private long totalResults;
}
