//package com.vibeverse.server.server.service;
//
//import com.vibeverse.server.server.dto.VibeBoardCreateDto;
//import com.vibeverse.server.server.dto.VibeBoardDto;
//import com.vibeverse.server.server.dto.VibeBoardUpdateDto;
//import com.vibeverse.server.server.entity.VibeBoard;
//import com.vibeverse.server.server.entity.Viber;
//import com.vibeverse.server.server.exception.ResourceNotFoundException;
//import com.vibeverse.server.server.exception.UnauthorizedException;
//import com.vibeverse.server.server.mapper.VibeBoardMapper;
//import com.vibeverse.server.server.repository.VibeBoardRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class VibeBoardService {
//
//    private final VibeBoardRepository vibeBoardRepository;
//    private final VibeBoardMapper vibeBoardMapper;
//    private final ViberService viberService;
//    
//    @Transactional(readOnly = true)
//    public List<VibeBoardDto> getCurrentViberBoards() {
//        Viber viber = viberService.getAuthenticatedViber();
//        List<VibeBoard> vibeBoards = vibeBoardRepository.findByViber(viber);
//        return vibeBoardMapper.toDtoList(vibeBoards);
//    }
//    
//    @Transactional(readOnly = true)
//    public List<VibeBoardDto> getViberBoardsByViberId(Long viberId) {
//        List<VibeBoard> vibeBoards = vibeBoardRepository.findByViberId(viberId);
//        return vibeBoardMapper.toDtoList(vibeBoards);
//    }
//    
//    @Transactional(readOnly = true)
//    public VibeBoardDto getVibeBoardById(Long id) {
//        VibeBoard vibeBoard = vibeBoardRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("VibeBoard", "id", id));
//        
//        return vibeBoardMapper.toDto(vibeBoard);
//    }
//    
//    @Transactional
//    public VibeBoardDto createVibeBoard(VibeBoardCreateDto vibeBoardCreateDto) {
//        Viber viber = viberService.getAuthenticatedViber();
//        
//        VibeBoard vibeBoard = vibeBoardMapper.toEntity(vibeBoardCreateDto);
//        vibeBoard.setViber(viber);
//        
//        vibeBoardRepository.save(vibeBoard);
//        
//        return vibeBoardMapper.toDto(vibeBoard);
//    }
//    
//    @Transactional
//    public VibeBoardDto updateVibeBoard(Long id, VibeBoardUpdateDto vibeBoardUpdateDto) {
//        VibeBoard vibeBoard = vibeBoardRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("VibeBoard", "id", id));
//        
//        // Check if the authenticated user owns this board
//        Viber viber = viberService.getAuthenticatedViber();
//        if (!vibeBoard.getViber().getId().equals(viber.getId())) {
//            throw new UnauthorizedException("You don't have permission to update this board");
//        }
//
//        vibeBoard.setAnimeList(animeRepository.findAllById(vibeBoardUpdateDto.getAnimeIds()));
//        vibeBoard.setMovieList(movieRepository.findAllById(vibeBoardUpdateDto.getMovieIds()));
//        vibeBoard.setBookList(ookRepository.findAllById(vibeBoardUpdateDto.getBookIds()));
//        vibeBoard.setGameList(GameRepository.findAllById(vibeBoardUpdateDto.getGameIds()));
//
//
//        vibeBoardMapper.updateEntity(vibeBoardUpdateDto, vibeBoard);
//        vibeBoardRepository.save(vibeBoard);
//        
//        return vibeBoardMapper.toDto(vibeBoard);
//    }
//    
//    @Transactional
//    public void deleteVibeBoard(Long id) {
//        VibeBoard vibeBoard = vibeBoardRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("VibeBoard", "id", id));
//        
//        // Check if the authenticated user owns this board
//        Viber viber = viberService.getAuthenticatedViber();
//        if (!vibeBoard.getViber().getId().equals(viber.getId())) {
//            throw new UnauthorizedException("You don't have permission to delete this board");
//        }
//        
//        vibeBoardRepository.delete(vibeBoard);
//    }
//}
package com.vibeverse.server.service;

import com.vibeverse.server.dto.*;
import com.vibeverse.server.entity.*;
import com.vibeverse.server.exception.BadRequestException;
import com.vibeverse.server.exception.ResourceNotFoundException;
import com.vibeverse.server.exception.UnauthorizedException;
import com.vibeverse.server.mapper.VibeBoardMapper;
import com.vibeverse.server.mapper.VibeBoardMediaMapper;
import com.vibeverse.server.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VibeBoardService {

    private final VibeBoardRepository vibeBoardRepository;
    private final VibeBoardMediaRepository vibeBoardMediaRepository;
    private final ViberAnimeRepository viberAnimeRepository;
    private final ViberMovieRepository viberMovieRepository;
    private final ViberBookRepository viberBookRepository;
    private final ViberGameRepository viberGameRepository;
    private final AnimeRepository animeRepository;
    private final MovieRepository movieRepository;
    private final BookRepository bookRepository;
    private final GameRepository gameRepository;
    private final VibeBoardMapper vibeBoardMapper;
    private final VibeBoardMediaMapper vibeBoardMediaMapper;
    private final ViberService viberService;
    private final MediaService mediaService;

    @Transactional(readOnly = true)
    public List<VibeBoardDto> getCurrentViberBoards() {
        Viber viber = viberService.getAuthenticatedViber();
        List<VibeBoard> vibeBoards = vibeBoardRepository.findByViber(viber);
        return vibeBoardMapper.toDtoList(vibeBoards);
    }

    @Transactional(readOnly = true)
    public List<VibeBoardDto> getViberBoardsByViberId(Long viberId) {
        List<VibeBoard> vibeBoards = vibeBoardRepository.findByViberId(viberId);
        return vibeBoardMapper.toDtoList(vibeBoards);
    }

    @Transactional(readOnly = true)
    public VibeBoardDto getVibeBoardById(Long id) {
        VibeBoard vibeBoard = vibeBoardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VibeBoard", "id", id));

        return vibeBoardMapper.toDto(vibeBoard);
    }

    @Transactional
    public VibeBoardDto createVibeBoard(VibeBoardCreateDto vibeBoardCreateDto) {
        Viber viber = viberService.getAuthenticatedViber();

        VibeBoard vibeBoard = vibeBoardMapper.toEntity(vibeBoardCreateDto);
        vibeBoard.setViber(viber);

        vibeBoardRepository.save(vibeBoard);

        return vibeBoardMapper.toDto(vibeBoard);
    }

    @Transactional
    public VibeBoardDto updateVibeBoard(Long id, VibeBoardUpdateDto vibeBoardUpdateDto) {
        VibeBoard vibeBoard = vibeBoardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VibeBoard", "id", id));

        // Check if the authenticated user owns this board
        Viber viber = viberService.getAuthenticatedViber();
        if (!vibeBoard.getViber().getId().equals(viber.getId())) {
            throw new UnauthorizedException("You don't have permission to update this board");
        }

        vibeBoardMapper.updateEntity(vibeBoardUpdateDto, vibeBoard);
        vibeBoardRepository.save(vibeBoard);

        return vibeBoardMapper.toDto(vibeBoard);
    }

    @Transactional
    public void deleteVibeBoard(Long id) {
        VibeBoard vibeBoard = vibeBoardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VibeBoard", "id", id));

        // Check if the authenticated user owns this board
        Viber viber = viberService.getAuthenticatedViber();
        if (!vibeBoard.getViber().getId().equals(viber.getId())) {
            throw new UnauthorizedException("You don't have permission to delete this board");
        }

        vibeBoardRepository.delete(vibeBoard);
    }

    // New methods for managing media in boards

    @Transactional(readOnly = true)
    public List<VibeBoardMediaDto> getBoardMedia(Long boardId) {
        // Check if the board exists and the user has access to it
        VibeBoard vibeBoard = vibeBoardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("VibeBoard", "id", boardId));

        // Check if the authenticated user owns this board or if the board is public
        Viber viber = viberService.getAuthenticatedViber();
        if (!vibeBoard.getViber().getId().equals(viber.getId())) {
            throw new UnauthorizedException("You don't have permission to view this board's media");
        }

        List<VibeBoardMedia> mediaItems = vibeBoardMediaRepository.findByVibeBoardId(boardId);
        List<VibeBoardMediaDto> mediaDtos = vibeBoardMediaMapper.toDtoList(mediaItems);

        // Populate media details based on type
        for (VibeBoardMediaDto mediaDto : mediaDtos) {
            switch (mediaDto.getMediaType()) {
                case ANIME:
                    Optional<Anime> anime = animeRepository.findById(mediaDto.getMediaId());
                    anime.ifPresent(a -> mediaDto.setAnimeDetails(mediaService.getAnimeById(a.getExternalId())));
                    break;
                case MOVIE:
                case SERIES:
                    Optional<Movie> movie = movieRepository.findById(mediaDto.getMediaId());
                    movie.ifPresent(m -> mediaDto.setMovieDetails(mediaService.getMovieById(m.getExternalId())));
                    break;
                case BOOK:
                    Optional<Book> book = bookRepository.findById(mediaDto.getMediaId());
                    book.ifPresent(b -> mediaDto.setBookDetails(mediaService.getBookById(b.getExternalId())));
                    break;
                case GAME:
                    Optional<Game> game = gameRepository.findById(mediaDto.getMediaId());
                    game.ifPresent(g -> mediaDto.setGameDetails(mediaService.getGameById(g.getExternalId())));
                    break;
            }
        }

        return mediaDtos;
    }

    @Transactional
    public VibeBoardMediaDto addMediaToBoard(Long boardId, AddMediaToBoardDto addMediaDto) {
        // Check if the board exists and the user has access to it
        VibeBoard vibeBoard = vibeBoardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("VibeBoard", "id", boardId));

        // Check if the authenticated user owns this board
        Viber viber = viberService.getAuthenticatedViber();
        if (!vibeBoard.getViber().getId().equals(viber.getId())) {
            throw new UnauthorizedException("You don't have permission to add media to this board");
        }

        // Check if the media exists in the user's library
        boolean mediaExists = false;
        switch (addMediaDto.getMediaType()) {
            case ANIME:
                mediaExists = viberAnimeRepository.findByViberIdAndAnimeId(viber.getId(), addMediaDto.getMediaId()).isPresent();
                break;
            case MOVIE:
            case SERIES:
                mediaExists = viberMovieRepository.findByViberIdAndMovieId(viber.getId(), addMediaDto.getMediaId()).isPresent();
                break;
            case BOOK:
                mediaExists = viberBookRepository.findByViberIdAndBookId(viber.getId(), addMediaDto.getMediaId()).isPresent();
                break;
            case GAME:
                mediaExists = viberGameRepository.findByViberIdAndGameId(viber.getId(), addMediaDto.getMediaId()).isPresent();
                break;
        }

        if (!mediaExists) {
            throw new BadRequestException("The specified media does not exist in your library");
        }

        // Check if the media is already in the board
        Optional<VibeBoardMedia> existingMedia = vibeBoardMediaRepository.findByVibeBoardIdAndMediaIdAndMediaType(
                boardId, addMediaDto.getMediaId(), addMediaDto.getMediaType());

        if (existingMedia.isPresent()) {
            throw new BadRequestException("This media is already in the board");
        }

        // Add the media to the board
        VibeBoardMedia vibeBoardMedia = VibeBoardMedia.builder()
                .vibeBoard(vibeBoard)
                .mediaId(addMediaDto.getMediaId())
                .mediaType(addMediaDto.getMediaType())
                .note(addMediaDto.getNote())
                .build();

        vibeBoardMediaRepository.save(vibeBoardMedia);

        VibeBoardMediaDto mediaDto = vibeBoardMediaMapper.toDto(vibeBoardMedia);

        // Populate media details based on type
        switch (addMediaDto.getMediaType()) {
            case ANIME:
                Optional<Anime> anime = animeRepository.findById(addMediaDto.getMediaId());
                anime.ifPresent(a -> mediaDto.setAnimeDetails(mediaService.getAnimeById(a.getExternalId())));
                break;
            case MOVIE:
            case SERIES:
                Optional<Movie> movie = movieRepository.findById(addMediaDto.getMediaId());
                movie.ifPresent(m -> mediaDto.setMovieDetails(mediaService.getMovieById(m.getExternalId())));
                break;
            case BOOK:
                Optional<Book> book = bookRepository.findById(addMediaDto.getMediaId());
                book.ifPresent(b -> mediaDto.setBookDetails(mediaService.getBookById(b.getExternalId())));
                break;
            case GAME:
                Optional<Game> game = gameRepository.findById(addMediaDto.getMediaId());
                game.ifPresent(g -> mediaDto.setGameDetails(mediaService.getGameById(g.getExternalId())));
                break;
        }

        return mediaDto;
    }

    @Transactional
    public void removeMediaFromBoard(Long boardId, Long mediaId, ViberMedia.MediaType mediaType) {
        // Check if the board exists and the user has access to it
        VibeBoard vibeBoard = vibeBoardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("VibeBoard", "id", boardId));

        // Check if the authenticated user owns this board
        Viber viber = viberService.getAuthenticatedViber();
        if (!vibeBoard.getViber().getId().equals(viber.getId())) {
            throw new UnauthorizedException("You don't have permission to remove media from this board");
        }

        // Check if the media is in the board
        VibeBoardMedia vibeBoardMedia = vibeBoardMediaRepository.findByVibeBoardIdAndMediaIdAndMediaType(
                        boardId, mediaId, mediaType)
                .orElseThrow(() -> new ResourceNotFoundException("Media", "id", mediaId));

        // Remove the media from the board
        vibeBoardMediaRepository.delete(vibeBoardMedia);
    }

    @Transactional
    public VibeBoardMediaDto updateMediaNote(Long boardId, Long mediaId, ViberMedia.MediaType mediaType, String note) {
        // Check if the board exists and the user has access to it
        VibeBoard vibeBoard = vibeBoardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("VibeBoard", "id", boardId));

        // Check if the authenticated user owns this board
        Viber viber = viberService.getAuthenticatedViber();
        if (!vibeBoard.getViber().getId().equals(viber.getId())) {
            throw new UnauthorizedException("You don't have permission to update media in this board");
        }

        // Check if the media is in the board
        VibeBoardMedia vibeBoardMedia = vibeBoardMediaRepository.findByVibeBoardIdAndMediaIdAndMediaType(
                        boardId, mediaId, mediaType)
                .orElseThrow(() -> new ResourceNotFoundException("Media", "id", mediaId));

        // Update the note
        vibeBoardMedia.setNote(note);
        vibeBoardMediaRepository.save(vibeBoardMedia);

        return vibeBoardMediaMapper.toDto(vibeBoardMedia);
    }
}