//package com.vibeverse.server.service;
//
//import com.vibeverse.server.dto.request.MediaRequestDto;
//import com.vibeverse.server.dto.response.MediaResponseDto;
//
//import java.util.List;
//import java.util.UUID;
//
//public interface MediaService {
//
//    /**
//     * Creates a new Media item.
//     *
//     * @param requestDto The DTO containing media details.
//     * @return The created Media as a Response DTO.
//     */
//    MediaResponseDto createMedia(MediaRequestDto requestDto);
//
//    /**
//     * Retrieves all Media items.
//     *
//     * @return A list of all Media items as Response DTOs.
//     */
//    List<MediaResponseDto> getAllMedia();
//
//    /**
//     * Retrieves a Media item by its ID.
//     *
//     * @param mediaId The ID of the Media item.
//     * @return The Media item as a Response DTO.
//     * @throws jakarta.persistence.EntityNotFoundException if the media item with the given ID is not found.
//     */
//    MediaResponseDto getMediaById(UUID mediaId);
//
//    /**
//     * Updates an existing Media item.
//     *
//     * @param mediaId The ID of the Media item to update.
//     * @param requestDto The DTO containing updated media details.
//     * @return The updated Media item as a Response DTO.
//     * @throws jakarta.persistence.EntityNotFoundException if the media item with the given ID is not found.
//     */
//    MediaResponseDto updateMedia(UUID mediaId, MediaRequestDto requestDto);
//
//    /**
//     * Deletes a Media item by its ID.
//     *
//     * @param mediaId The ID of the Media item to delete.
//     * @throws jakarta.persistence.EntityNotFoundException if the media item with the given ID is not found.
//     */
//    void deleteMedia(UUID mediaId);
//}