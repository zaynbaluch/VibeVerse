package com.vibeverse.server.service;

import com.vibeverse.server.dto.request.MediaRequestDto;
import com.vibeverse.server.dto.response.MediaResponseDto;
import com.vibeverse.server.exception.ResourceNotFoundException;
import com.vibeverse.server.model.Media;
import com.vibeverse.server.repository.MediaRepository;
import com.vibeverse.server.dto.mapper.MediaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final MediaRepository mediaRepository;
    private final MediaMapper mediaMapper;

    @Transactional
    public MediaResponseDto createMedia(MediaRequestDto dto) {
        if (mediaRepository.existsByTitle(dto.getTitle())) {
            throw new IllegalArgumentException("Media with this title already exists");
        }
        Media media = mediaMapper.toEntity(dto);
        return mediaMapper.toResponseDto(mediaRepository.save(media));
    }

    @Transactional(readOnly = true)
    public MediaResponseDto getMediaById(UUID id) {
        Media media = mediaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Media not found with id: " + id));
        return mediaMapper.toResponseDto(media);
    }

    @Transactional(readOnly = true)
    public List<MediaResponseDto> getAllMedia() {
        return mediaRepository.findAll().stream()
                .map(mediaMapper::toResponseDto)
                .toList();
    }

    @Transactional
    public MediaResponseDto updateMedia(UUID id, MediaRequestDto dto) {
        Media media = mediaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Media not found with id: " + id));

        mediaMapper.updateEntityFromDto(dto, media);
        return mediaMapper.toResponseDto(mediaRepository.save(media));
    }

    @Transactional
    public void deleteMedia(UUID id) {
        if (!mediaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Media not found with id: " + id);
        }
        mediaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<MediaResponseDto> searchMediaByTitle(String title) {
        return mediaRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(mediaMapper::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MediaResponseDto> getMediaWithImages() {
        return mediaRepository.findByImageUrlIsNotNull().stream()
                .map(mediaMapper::toResponseDto)
                .toList();
    }
}