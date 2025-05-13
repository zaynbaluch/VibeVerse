package com.vibeverse.server.service;

import com.vibeverse.server.dto.*;
import com.vibeverse.server.entity.*;
import com.vibeverse.server.exception.ResourceNotFoundException;
import com.vibeverse.server.external.client.ExternalApiClient;
import com.vibeverse.server.external.client.GoogleBooksApiClient;
import com.vibeverse.server.external.client.JikanApiClient;
import com.vibeverse.server.external.client.RawgApiClient;
import com.vibeverse.server.external.client.TmdbApiClient;
import com.vibeverse.server.mapper.*;
import com.vibeverse.server.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final ViberService viberService;
    
    private final AnimeRepository animeRepository;
    private final MovieRepository movieRepository;
    private final BookRepository bookRepository;
    private final GameRepository gameRepository;
    
    private final ViberAnimeRepository viberAnimeRepository;
    private final ViberMovieRepository viberMovieRepository;
    private final ViberBookRepository viberBookRepository;
    private final ViberGameRepository viberGameRepository;
    
    private final AnimeMapper animeMapper;
    private final MovieMapper movieMapper;
    private final BookMapper bookMapper;
    private final GameMapper gameMapper;
    
    private final ViberAnimeMapper viberAnimeMapper;
    private final ViberMovieMapper viberMovieMapper;
    private final ViberBookMapper viberBookMapper;
    private final ViberGameMapper viberGameMapper;
    
    private final JikanApiClient jikanApiClient;
    private final TmdbApiClient tmdbApiClient;
    private final GoogleBooksApiClient googleBooksApiClient;
    private final RawgApiClient rawgApiClient;


    @Transactional(readOnly = true)
    public List<ViberAnimeDto> getAllViberAnimeForCurrentUser() {
        Viber viber = viberService.getAuthenticatedViber();
        return viberAnimeRepository.findByViberId(viber.getId())
                .stream()
                .map(viberAnimeMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ViberMovieDto> getAllViberMoviesForCurrentUser() {
        Viber viber = viberService.getAuthenticatedViber();
        return viberMovieRepository.findByViberId(viber.getId())
                .stream()
                .map(viberMovieMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ViberBookDto> getAllViberBooksForCurrentUser() {
        Viber viber = viberService.getAuthenticatedViber();
        return viberBookRepository.findByViberId(viber.getId())
                .stream()
                .map(viberBookMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ViberGameDto> getAllViberGamesForCurrentUser() {
        Viber viber = viberService.getAuthenticatedViber();
        return viberGameRepository.findByViberId(viber.getId())
                .stream()
                .map(viberGameMapper::toDto)
                .toList();
    }


    // Search methods
    @Transactional(readOnly = true)
    public MediaSearchResultDto<AnimeDto> searchAnime(MediaSearchDto searchDto) {
        return jikanApiClient.search(searchDto.getQuery(), searchDto.getPage() != null ? searchDto.getPage() : 1);
    }
    
    @Transactional(readOnly = true)
    public MediaSearchResultDto<MovieDto> searchMovies(MediaSearchDto searchDto) {
        return tmdbApiClient.search(searchDto.getQuery(), searchDto.getPage() != null ? searchDto.getPage() : 1);
    }
    
    @Transactional(readOnly = true)
    public MediaSearchResultDto<BookDto> searchBooks(MediaSearchDto searchDto) {
        return googleBooksApiClient.search(searchDto.getQuery(), searchDto.getPage() != null ? searchDto.getPage() : 1);
    }
    
    @Transactional(readOnly = true)
    public MediaSearchResultDto<GameDto> searchGames(MediaSearchDto searchDto) {
        return rawgApiClient.search(searchDto.getQuery(), searchDto.getPage() != null ? searchDto.getPage() : 1);
    }
    
    // Get by ID methods
    @Transactional(readOnly = true)
    public AnimeDto getAnimeById(String externalId) {
        // First check if we have it in our database
        Optional<Anime> existingAnime = animeRepository.findByExternalId(externalId);
        if (existingAnime.isPresent()) {
            return animeMapper.toDto(existingAnime.get());
        }
        
        // If not, fetch from external API
        return jikanApiClient.getById(externalId);
    }
    
    @Transactional(readOnly = true)
    public MovieDto getMovieById(String externalId) {
        // First check if we have it in our database
        Optional<Movie> existingMovie = movieRepository.findByExternalId(externalId);
        if (existingMovie.isPresent()) {
            return movieMapper.toDto(existingMovie.get());
        }
        
        // If not, fetch from external API
        return tmdbApiClient.getById(externalId);
    }
    
    @Transactional(readOnly = true)
    public BookDto getBookById(String externalId) {
        // First check if we have it in our database
        Optional<Book> existingBook = bookRepository.findByExternalId(externalId);
        if (existingBook.isPresent()) {
            return bookMapper.toDto(existingBook.get());
        }
        
        // If not, fetch from external API
        return googleBooksApiClient.getById(externalId);
    }
    
    @Transactional(readOnly = true)
    public GameDto getGameById(String externalId) {
        // First check if we have it in our database
        Optional<Game> existingGame = gameRepository.findByExternalId(externalId);
        if (existingGame.isPresent()) {
            return gameMapper.toDto(existingGame.get());
        }
        
        // If not, fetch from external API
        return rawgApiClient.getById(externalId);
    }
    
    // Add to viber's library methods
    @Transactional
    public ViberAnimeDto addAnimeToLibrary(String externalId, ViberAnimeUpdateDto updateDto) {
        Viber viber = viberService.getAuthenticatedViber();
        
        // Check if the anime exists in our database
        Anime anime;
        Optional<Anime> existingAnime = animeRepository.findByExternalId(externalId);
        
        if (existingAnime.isPresent()) {
            anime = existingAnime.get();
        } else {
            // Fetch from external API and save to our database
            AnimeDto animeDto = jikanApiClient.getById(externalId);
            anime = animeMapper.toEntity(animeDto);
            animeRepository.save(anime);
        }
        
        // Check if the viber already has this anime in their library
        Optional<ViberAnime> existingViberAnime = viberAnimeRepository.findByViberIdAndAnimeId(viber.getId(), anime.getId());
        
        ViberAnime viberAnime;
        if (existingViberAnime.isPresent()) {
            viberAnime = existingViberAnime.get();
            viberAnimeMapper.updateEntity(updateDto, viberAnime);
        } else {
            viberAnime = new ViberAnime();
            viberAnime.setViber(viber);
            viberAnime.setAnime(anime);
            viberAnime.setMediaId(anime.getId());
            viberAnime.setExternalId(anime.getExternalId());
            viberAnime.setExternalApi(anime.getExternalApi());
            viberAnime.setType(ViberMedia.MediaType.ANIME);
            viberAnimeMapper.updateEntity(updateDto, viberAnime);
        }
        
        viberAnimeRepository.save(viberAnime);
        
        return viberAnimeMapper.toDto(viberAnime);
    }
    
    @Transactional
    public ViberMovieDto addMovieToLibrary(String externalId, ViberMovieUpdateDto updateDto) {
        Viber viber = viberService.getAuthenticatedViber();
        
        // Check if the movie exists in our database
        Movie movie;
        Optional<Movie> existingMovie = movieRepository.findByExternalId(externalId);
        
        if (existingMovie.isPresent()) {
            movie = existingMovie.get();
        } else {
            // Fetch from external API and save to our database
            MovieDto movieDto = tmdbApiClient.getById(externalId);
            movie = movieMapper.toEntity(movieDto);
            movieRepository.save(movie);
        }
        
        // Check if the viber already has this movie in their library
        Optional<ViberMovie> existingViberMovie = viberMovieRepository.findByViberIdAndMovieId(viber.getId(), movie.getId());
        
        ViberMovie viberMovie;
        if (existingViberMovie.isPresent()) {
            viberMovie = existingViberMovie.get();
            viberMovieMapper.updateEntity(updateDto, viberMovie);
        } else {
            viberMovie = new ViberMovie();
            viberMovie.setViber(viber);
            viberMovie.setMovie(movie);
            viberMovie.setMediaId(movie.getId());
            viberMovie.setExternalId(movie.getExternalId());
            viberMovie.setExternalApi(movie.getExternalApi());
            viberMovie.setType(movie.getType() == Movie.MediaType.MOVIE ? ViberMedia.MediaType.MOVIE : ViberMedia.MediaType.SERIES);
            viberMovieMapper.updateEntity(updateDto, viberMovie);
        }
        
        viberMovieRepository.save(viberMovie);
        
        return viberMovieMapper.toDto(viberMovie);
    }
    
    @Transactional
    public ViberBookDto addBookToLibrary(String externalId, ViberBookUpdateDto updateDto) {
        Viber viber = viberService.getAuthenticatedViber();
        
        // Check if the book exists in our database
        Book book;
        Optional<Book> existingBook = bookRepository.findByExternalId(externalId);
        
        if (existingBook.isPresent()) {
            book = existingBook.get();
        } else {
            // Fetch from external API and save to our database
            BookDto bookDto = googleBooksApiClient.getById(externalId);
            book = bookMapper.toEntity(bookDto);
            bookRepository.save(book);
        }
        
        // Check if the viber already has this book in their library
        Optional<ViberBook> existingViberBook = viberBookRepository.findByViberIdAndBookId(viber.getId(), book.getId());
        
        ViberBook viberBook;
        if (existingViberBook.isPresent()) {
            viberBook = existingViberBook.get();
            viberBookMapper.updateEntity(updateDto, viberBook);
        } else {
            viberBook = new ViberBook();
            viberBook.setViber(viber);
            viberBook.setBook(book);
            viberBook.setMediaId(book.getId());
            viberBook.setExternalId(book.getExternalId());
            viberBook.setExternalApi(book.getExternalApi());
            viberBook.setType(ViberMedia.MediaType.BOOK);
            viberBookMapper.updateEntity(updateDto, viberBook);
        }
        
        viberBookRepository.save(viberBook);
        
        return viberBookMapper.toDto(viberBook);
    }
    
    @Transactional
    public ViberGameDto addGameToLibrary(String externalId, ViberGameUpdateDto updateDto) {
        Viber viber = viberService.getAuthenticatedViber();
        
        // Check if the game exists in our database
        Game game;
        Optional<Game> existingGame = gameRepository.findByExternalId(externalId);
        
        if (existingGame.isPresent()) {
            game = existingGame.get();
        } else {
            // Fetch from external API and save to our database
            GameDto gameDto = rawgApiClient.getById(externalId);
            game = gameMapper.toEntity(gameDto);
            gameRepository.save(game);
        }
        
        // Check if the viber already has this game in their library
        Optional<ViberGame> existingViberGame = viberGameRepository.findByViberIdAndGameId(viber.getId(), game.getId());
        
        ViberGame viberGame;
        if (existingViberGame.isPresent()) {
            viberGame = existingViberGame.get();
            viberGameMapper.updateEntity(updateDto, viberGame);
        } else {
            viberGame = new ViberGame();
            viberGame.setViber(viber);
            viberGame.setGame(game);
            viberGame.setMediaId(game.getId());
            viberGame.setExternalId(game.getExternalId());
            viberGame.setExternalApi(game.getExternalApi());
            viberGame.setType(ViberMedia.MediaType.GAME);
            viberGameMapper.updateEntity(updateDto, viberGame);
        }
        
        viberGameRepository.save(viberGame);
        
        return viberGameMapper.toDto(viberGame);
    }
    
    // Update viber's library methods
    @Transactional
    public ViberAnimeDto updateAnimeInLibrary(Long id, ViberAnimeUpdateDto updateDto) {
        Viber viber = viberService.getAuthenticatedViber();
        
        ViberAnime viberAnime = viberAnimeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ViberAnime", "id", id));
        
        // Check if the viber owns this entry
        if (!viberAnime.getViber().getId().equals(viber.getId())) {
            throw new ResourceNotFoundException("ViberAnime", "id", id);
        }
        
        viberAnimeMapper.updateEntity(updateDto, viberAnime);
        viberAnimeRepository.save(viberAnime);
        
        return viberAnimeMapper.toDto(viberAnime);
    }
    
    @Transactional
    public ViberMovieDto updateMovieInLibrary(Long id, ViberMovieUpdateDto updateDto) {
        Viber viber = viberService.getAuthenticatedViber();
        
        ViberMovie viberMovie = viberMovieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ViberMovie", "id", id));
        
        // Check if the viber owns this entry
        if (!viberMovie.getViber().getId().equals(viber.getId())) {
            throw new ResourceNotFoundException("ViberMovie", "id", id);
        }
        
        viberMovieMapper.updateEntity(updateDto, viberMovie);
        viberMovieRepository.save(viberMovie);
        
        return viberMovieMapper.toDto(viberMovie);
    }



    @Transactional
    public ViberBookDto updateBookInLibrary(Long id, ViberBookUpdateDto updateDto) {
        Viber viber = viberService.getAuthenticatedViber();
        
        ViberBook viberBook = viberBookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ViberBook", "id", id));
        
        // Check if the viber owns this entry
        if (!viberBook.getViber().getId().equals(viber.getId())) {
            throw new ResourceNotFoundException("ViberBook", "id", id);
        }
        
        viberBookMapper.updateEntity(updateDto, viberBook);
        viberBookRepository.save(viberBook);
        
        return viberBookMapper.toDto(viberBook);
    }
    
    @Transactional
    public ViberGameDto updateGameInLibrary(Long id, ViberGameUpdateDto updateDto) {
        Viber viber = viberService.getAuthenticatedViber();
        
        ViberGame viberGame = viberGameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ViberGame", "id", id));
        
        // Check if the viber owns this entry
        if (!viberGame.getViber().getId().equals(viber.getId())) {
            throw new ResourceNotFoundException("ViberGame", "id", id);
        }
        
        viberGameMapper.updateEntity(updateDto, viberGame);
        viberGameRepository.save(viberGame);
        
        return viberGameMapper.toDto(viberGame);
    }
    
    // Remove from viber's library methods
    @Transactional
    public void removeAnimeFromLibrary(Long id) {
        Viber viber = viberService.getAuthenticatedViber();
        
        ViberAnime viberAnime = viberAnimeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ViberAnime", "id", id));
        
        // Check if the viber owns this entry
        if (!viberAnime.getViber().getId().equals(viber.getId())) {
            throw new ResourceNotFoundException("ViberAnime", "id", id);
        }
        
        viberAnimeRepository.delete(viberAnime);
    }
    
    @Transactional
    public void removeMovieFromLibrary(Long id) {
        Viber viber = viberService.getAuthenticatedViber();
        
        ViberMovie viberMovie = viberMovieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ViberMovie", "id", id));
        
        // Check if the viber owns this entry
        if (!viberMovie.getViber().getId().equals(viber.getId())) {
            throw new ResourceNotFoundException("ViberMovie", "id", id);
        }
        
        viberMovieRepository.delete(viberMovie);
    }
    
    @Transactional
    public void removeBookFromLibrary(Long id) {
        Viber viber = viberService.getAuthenticatedViber();
        
        ViberBook viberBook = viberBookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ViberBook", "id", id));
        
        // Check if the viber owns this entry
        if (!viberBook.getViber().getId().equals(viber.getId())) {
            throw new ResourceNotFoundException("ViberBook", "id", id);
        }
        
        viberBookRepository.delete(viberBook);
    }
    
    @Transactional
    public void removeGameFromLibrary(Long id) {
        Viber viber = viberService.getAuthenticatedViber();
        
        ViberGame viberGame = viberGameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ViberGame", "id", id));
        
        // Check if the viber owns this entry
        if (!viberGame.getViber().getId().equals(viber.getId())) {
            throw new ResourceNotFoundException("ViberGame", "id", id);
        }
        
        viberGameRepository.delete(viberGame);
    }
}
