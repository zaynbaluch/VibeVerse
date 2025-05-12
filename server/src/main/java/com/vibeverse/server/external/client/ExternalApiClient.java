package com.vibeverse.server.external.client;

import com.vibeverse.server.dto.MediaSearchResultDto;

public interface ExternalApiClient<T> {
    MediaSearchResultDto<T> search(String query, int page);
    T getById(String id);
}
