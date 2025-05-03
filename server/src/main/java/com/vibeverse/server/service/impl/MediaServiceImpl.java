//package com.vibeverse.server.service.impl;
//
//import com.vibeverse.server.dto.request.MediaRequestDto;
//import com.vibeverse.server.dto.response.MediaResponseDto;
//import com.vibeverse.server.mapper.MediaMapper; // Import the mapper
//import com.vibeverse.server.model.Media;
//import com.vibeverse.server.repository.MediaRepository;
//import com.vibeverse.server.service.MediaService;
//import jakarta.persistence.EntityNotFoundException; // Import EntityNotFoundException
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.UUID;
//// Removed manual mapping method and unused imports
//
//@Service
//@RequiredArgsConstructor // Injects final fields (MediaRepository, MediaMapper)
//@Transactional // Apply transactional behavior to service methods by default
//public class MediaServiceImpl implements MediaService {
//
//    private final MediaRepository mediaRepository;
//    private final MediaMapper mediaMapper; // Inject the MapStruct mapper
//
//    @Override
//    public MediaResponseDto createMedia(MediaRequestDto requestDto) {
//        // Use MapStruct to convert the Request DTO to the Media entity
//        // MapStruct handles List<String> and Map<String, Object> mapping automatically
//        Media media = mediaMapper.toEntity(requestDto);
//
//        // Save the new entity to the database
//        Media savedMedia = mediaRepository.save(media);
//
//        // Use MapStruct to convert the saved entity back to the Response DTO
//        return mediaMapper.toDto(savedMedia);
//    }
//
//    @Override
//    @Transactional(readOnly = true) // Read-only transaction for fetching data
//    public List<MediaResponseDto> getAllMedia() {
//        // Fetch all entities
//        List<Media> mediaList = mediaRepository.findAll();
//        // Use MapStruct to convert the list of entities to a list of Response DTOs
//        return mediaMapper.toDtoList(mediaList);
//    }
//
//    @Override
//    @Transactional(readOnly = true) // Read-only transaction for fetching data
//    public MediaResponseDto getMediaById(UUID mediaId) {
//        // Find entity by ID, or throw EntityNotFoundException if not found
//        Media media = mediaRepository.findById(mediaId)
//                // Changed RuntimeException to EntityNotFoundException for consistency
//                .orElseThrow(() -> new EntityNotFoundException("Media not found with ID: " + mediaId));
//        // Use MapStruct to convert the entity to a Response DTO
//        return mediaMapper.toDto(media);
//    }
//
//    @Override
//    public MediaResponseDto updateMedia(UUID mediaId, MediaRequestDto requestDto) {
//        // Find the existing entity by ID
//        Media existingMedia = mediaRepository.findById(mediaId)
//                // Changed RuntimeException to EntityNotFoundException for consistency
//                .orElseThrow(() -> new EntityNotFoundException("Media not found with ID: " + mediaId));
//
//        // Use MapStruct to update the fields of the existing entity from the Request DTO
//        // MapStruct handles transferring fields based on the mapper definition
//        mediaMapper.updateEntityFromDto(requestDto, existingMedia);
//
//        // Save the updated entity (JPA will perform an update)
//        // The @UpdateTimestamp on the entity will automatically be set here
//        Media updatedMedia = mediaRepository.save(existingMedia);
//
//        // Use MapStruct to convert the updated entity back to the Response DTO
//        return mediaMapper.toDto(updatedMedia);
//    }
//
//    @Override
//    public void deleteMedia(UUID mediaId) {
//        // Check if the entity exists before deleting
//        // This gives a specific EntityNotFoundException
//        if (!mediaRepository.existsById(mediaId)) {
//            // Changed RuntimeException to EntityNotFoundException for consistency
//            throw new EntityNotFoundException("Media not found with ID: " + mediaId);
//        }
//        // Delete the entity by ID
//        mediaRepository.deleteById(mediaId);
//    }
//}