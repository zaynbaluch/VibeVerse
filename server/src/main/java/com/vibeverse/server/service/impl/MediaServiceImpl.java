package com.vibeverse.server.service.impl;

import com.vibeverse.server.dto.request.MediaRequestDto;
import com.vibeverse.server.dto.response.MediaResponseDto;
import com.vibeverse.server.model.Media;
import com.vibeverse.server.repository.MediaRepository;
import com.vibeverse.server.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MediaServiceImpl implements MediaService {

    private final MediaRepository mediaRepository;

    @Override
    public MediaResponseDto createMedia(MediaRequestDto requestDto) {
        Media media = Media.builder()
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .imageUrl(requestDto.getImageUrl())
                .tags(requestDto.getTags())
                .specificData(requestDto.getSpecificData())
                .build();

        Media saved = mediaRepository.save(media);
        return mapToResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MediaResponseDto> getAllMedia() {
        return mediaRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MediaResponseDto getMediaById(UUID mediaId) {
        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new RuntimeException("Media not found with ID: " + mediaId));
        return mapToResponseDto(media);
    }

    @Override
    public MediaResponseDto updateMedia(UUID mediaId, MediaRequestDto requestDto) {
        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new RuntimeException("Media not found with ID: " + mediaId));

        media.setTitle(requestDto.getTitle());
        media.setDescription(requestDto.getDescription());
        media.setImageUrl(requestDto.getImageUrl());
        media.setTags(requestDto.getTags());
        media.setSpecificData(requestDto.getSpecificData());

        Media updated = mediaRepository.save(media);
        return mapToResponseDto(updated);
    }

    @Override
    public void deleteMedia(UUID mediaId) {
        if (!mediaRepository.existsById(mediaId)) {
            throw new RuntimeException("Media not found with ID: " + mediaId);
        }
        mediaRepository.deleteById(mediaId);
    }

    private MediaResponseDto mapToResponseDto(Media media) {
        return MediaResponseDto.builder()
                .mediaId(media.getMediaId())
                .title(media.getTitle())
                .description(media.getDescription())
                .imageUrl(media.getImageUrl())
                .tags(media.getTags())
                .specificData(media.getSpecificData())
                .createdAt(media.getCreatedAt())
                .updatedAt(media.getUpdatedAt())
                .build();
    }
}
