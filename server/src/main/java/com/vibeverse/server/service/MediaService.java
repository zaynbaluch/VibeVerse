package com.vibeverse.server.service;

import com.vibeverse.server.dto.request.MediaRequestDto;
import com.vibeverse.server.dto.response.MediaResponseDto;

import java.util.List;
import java.util.UUID;

public interface MediaService {

    MediaResponseDto createMedia(MediaRequestDto requestDto);

    List<MediaResponseDto> getAllMedia();

    MediaResponseDto getMediaById(UUID mediaId);

    MediaResponseDto updateMedia(UUID mediaId, MediaRequestDto requestDto);

    void deleteMedia(UUID mediaId);
}
