package com.vibeverse.server.external.client;

import com.vibeverse.server.dto.GameDto;
import com.vibeverse.server.dto.MediaSearchResultDto;
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
public class RawgApiClient implements ExternalApiClient<GameDto> {

    private final RestTemplate restTemplate;
    
    @Value("${external.api.rawg.base-url}")
    private String baseUrl;
    
    @Value("${external.api.rawg.api-key}")
    private String apiKey;
    
    @Override
    public MediaSearchResultDto<GameDto> search(String query, int page) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/games")
                    .queryParam("key", apiKey)
                    .queryParam("search", query)
                    .queryParam("page", page)
                    .queryParam("page_size", 20)
                    .toUriString();
            
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            
            if (response == null) {
                throw new ExternalApiException("Failed to get response from RAWG API");
            }
            
            List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");
            
            List<GameDto> gameResults = new ArrayList<>();
            
            for (Map<String, Object> result : results) {
                gameResults.add(mapToGameDto(result));
            }
            
            int count = ((Integer) response.get("count"));
            int nextPage = response.containsKey("next") && response.get("next") != null ? page + 1 : page;
            int totalPages = (int) Math.ceil((double) count / 20);
            
            return MediaSearchResultDto.<GameDto>builder()
                    .results(gameResults)
                    .page(page)
                    .totalPages(totalPages)
                    .totalResults(count)
                    .build();
            
        } catch (Exception e) {
            log.error("Error searching games: {}", e.getMessage());
            throw new ExternalApiException("Failed to search games", e);
        }
    }
    
    @Override
    public GameDto getById(String id) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/games/" + id)
                    .queryParam("key", apiKey)
                    .toUriString();
            
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            
            if (response == null) {
                throw new ExternalApiException("Failed to get response from RAWG API");
            }
            
            return mapToGameDto(response);
            
        } catch (Exception e) {
            log.error("Error getting game by ID: {}", e.getMessage());
            throw new ExternalApiException("Failed to get game by ID", e);
        }
    }
    
    private GameDto mapToGameDto(Map<String, Object> data) {
        Integer id = (Integer) data.get("id");
        String name = (String) data.get("name");
        String description = (String) data.get("description");
        
        // Developer
        String developer = null;
        if (data.containsKey("developers")) {
            List<Map<String, Object>> developers = (List<Map<String, Object>>) data.get("developers");
            if (developers != null && !developers.isEmpty()) {
                developer = (String) developers.get(0).get("name");
            }
        }
        
        // Platforms
        HashSet<String> platforms = new HashSet<>();
        List<Map<String, Object>> platformsList = (List<Map<String, Object>>) data.get("platforms");
        if (platformsList != null) {
            for (Map<String, Object> platform : platformsList) {
                Map<String, Object> platformData = (Map<String, Object>) platform.get("platform");
                platforms.add((String) platformData.get("name"));
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
        String releaseDateStr = (String) data.get("released");
        LocalDate releaseDate = releaseDateStr != null && !releaseDateStr.isEmpty() ? LocalDate.parse(releaseDateStr) : null;
        
        // Image
        String imageUrl = (String) data.get("background_image");
        
        // Rating
        Double rating = data.containsKey("rating") ? (Double) data.get("rating") : null;
        
        return GameDto.builder()
                .title(name)
                .description(description)
                .developer(developer)
                .platforms(platforms)
                .genres(genres)
                .releaseDate(releaseDate)
                .coverImage(imageUrl)
                .externalId(id.toString())
                .externalApi(ViberMedia.ExternalApiType.RAWG)
                .rating(rating)
                .build();
    }
}
