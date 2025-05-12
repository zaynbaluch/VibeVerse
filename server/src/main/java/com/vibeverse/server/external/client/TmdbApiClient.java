package com.vibeverse.server.external.client;

import com.vibeverse.server.dto.MediaSearchResultDto;
import com.vibeverse.server.dto.MovieDto;
import com.vibeverse.server.entity.Movie;
import com.vibeverse.server.entity.ViberMedia;
import com.vibeverse.server.exception.ExternalApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class TmdbApiClient implements ExternalApiClient<MovieDto> {

    private final RestTemplate restTemplate;
    
    @Value("${external.api.tmdb.base-url}")
    private String baseUrl;
    
    @Value("${external.api.tmdb.api-key}")
    private String apiKey;
    
    @Override
    public MediaSearchResultDto<MovieDto> search(String query, int page) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/search/multi")
                    .queryParam("api_key", apiKey)
                    .queryParam("query", query)
                    .queryParam("page", page)
                    .toUriString();
            
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            
            if (response == null) {
                throw new ExternalApiException("Failed to get response from TMDB API");
            }
            
            List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");
            
            List<MovieDto> movieResults = new ArrayList<>();
            
            for (Map<String, Object> result : results) {
                String mediaType = (String) result.get("media_type");
                if ("movie".equals(mediaType) || "tv".equals(mediaType)) {
                    movieResults.add(mapToMovieDto(result, mediaType));
                }
            }
            
            int currentPage = (Integer) response.get("page");
            int totalPages = (Integer) response.get("total_pages");
            int totalResults = (Integer) response.get("total_results");
            
            return MediaSearchResultDto.<MovieDto>builder()
                    .results(movieResults)
                    .page(currentPage)
                    .totalPages(totalPages)
                    .totalResults(totalResults)
                    .build();
            
        } catch (Exception e) {
            log.error("Error searching movies: {}", e.getMessage());
            throw new ExternalApiException("Failed to search movies", e);
        }
    }
    
    @Override
    public MovieDto getById(String id) {
        try {
            // ID format: "movie/123" or "tv/456"
            String[] parts = id.split("@");
            String mediaType = parts[0];
            String mediaId = parts[1];
            
            String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/" + mediaType + "/" + mediaId)
                    .queryParam("api_key", apiKey)
                    .queryParam("append_to_response", "credits")
                    .toUriString();

            System.out.println(url);

            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            
            if (response == null) {
                throw new ExternalApiException("Failed to get response from TMDB API");
            }
            
            return mapToMovieDto(response, mediaType);
            
        } catch (Exception e) {
            log.error("Error getting movie by ID: {}", e.getMessage());
            throw new ExternalApiException("Failed to get movie by ID", e);
        }
    }
    
    private MovieDto mapToMovieDto(Map<String, Object> data, String mediaType) {
        Integer id = (Integer) data.get("id");
        String title = "movie".equals(mediaType) ? (String) data.get("title") : (String) data.get("name");
        String overview = (String) data.get("overview");
        
        // Director (for movies) or creator (for TV shows)
        String director = null;
        if (data.containsKey("credits")) {
            Map<String, Object> credits = (Map<String, Object>) data.get("credits");
            List<Map<String, Object>> crew = (List<Map<String, Object>>) credits.get("crew");
            
            if (crew != null) {
                for (Map<String, Object> member : crew) {
                    String job = (String) member.get("job");
                    if ("Director".equals(job) || "Creator".equals(job)) {
                        director = (String) member.get("name");
                        break;
                    }
                }
            }
        }
        
        // Cast
        HashSet<String> cast = new HashSet<>();
        if (data.containsKey("credits")) {
            Map<String, Object> credits = (Map<String, Object>) data.get("credits");
            List<Map<String, Object>> castList = (List<Map<String, Object>>) credits.get("cast");
            
            if (castList != null) {
                int count = 0;
                for (Map<String, Object> actor : castList) {
                    cast.add((String) actor.get("name"));
                    count++;
                    if (count >= 5) break; // Limit to 5 actors
                }
            }
        }
        
        // Genres
        HashSet<String> genres = new HashSet<>();
        List<Map<String, Object>> genresList = (List<Map<String, Object>>) data.get("genres");
        if (genresList != null) {
            for (Map<String, Object> genre : genresList) {
                genres.add((String) genre.get("name"));
            }
        }
        
        // Release date
        String releaseDateStr = "movie".equals(mediaType) ? (String) data.get("release_date") : (String) data.get("first_air_date");
        LocalDate releaseDate = releaseDateStr != null && !releaseDateStr.isEmpty() ? LocalDate.parse(releaseDateStr) : null;
        
        // Poster
        String posterPath = (String) data.get("poster_path");
        String imageUrl = posterPath != null ? "https://image.tmdb.org/t/p/w500" + posterPath : null;
        
        // Runtime
        Integer runtime = (Integer) data.get("runtime");
        
        // Media type
        Movie.MediaType type = "movie".equals(mediaType) ? Movie.MediaType.MOVIE : Movie.MediaType.SERIES;
        
        return MovieDto.builder()
                .title(title)
                .description(overview)
                .director(director)
                .cast(cast)
                .genres(genres)
                .releaseDate(releaseDate)
                .coverImage(imageUrl)
                .externalId(mediaType + "@" + id)
                .externalApi(ViberMedia.ExternalApiType.TMDB)
                .runtime(runtime)
                .type(type)
                .build();
    }
}
