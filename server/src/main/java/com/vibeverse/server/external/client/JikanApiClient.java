//package com.vibeverse.server.external.client;
//
//import com.vibeverse.server.dto.AnimeDto;
//import com.vibeverse.server.dto.MediaSearchResultDto;
//import com.vibeverse.server.entity.Anime;
//import com.vibeverse.server.entity.ViberMedia;
//import com.vibeverse.server.exception.ExternalApiException;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class JikanApiClient implements ExternalApiClient<AnimeDto> {
//
//    private final RestTemplate restTemplate;
//
//    @Value("${external.api.jikan.base-url}")
//    private String baseUrl;
//
//    @Override
//    public MediaSearchResultDto<AnimeDto> search(String query, int page) {
//        try {
//            String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/anime")
//                    .queryParam("q", query)
//                    .queryParam("page", page)
//                    .queryParam("limit", 20)
//                    .toUriString();
//
//            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
//
//            if (response == null) {
//                throw new ExternalApiException("Failed to get response from Jikan API");
//            }
//
//            List<Map<String, Object>> data = (List<Map<String, Object>>) response.get("data");
//            Map<String, Object> pagination = (Map<String, Object>) response.get("pagination");
//
//            List<AnimeDto> results = new ArrayList<>();
//
//            for (Map<String, Object> animeData : data) {
//                results.add(mapToAnimeDto(animeData));
//            }
//
//            int currentPage = (Integer) pagination.get("current_page");
//            int lastPage = (Integer) pagination.get("last_visible_page");
//
//            Map<String, Object> items = (Map<String, Object>) pagination.get("items");
//            int totalItems = items.containsKey("total") ? (Integer) items.get("total") : 0;
//
//            return MediaSearchResultDto.<AnimeDto>builder()
//                    .results(results)
//                    .page(currentPage)
//                    .totalPages(lastPage)
//                    .totalResults(totalItems)
//                    .build();
//
//        } catch (Exception e) {
//            log.error("Error searching anime: {}", e.getMessage());
//            throw new ExternalApiException("Failed to search anime", e);
//        }
//    }
//
//    @Override
//    public AnimeDto getById(String id) {
//        try {
//            String url = baseUrl + "/anime/" + id;
//            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
//
//            if (response == null) {
//                throw new ExternalApiException("Failed to get response from Jikan API");
//            }
//
//            Map<String, Object> data = (Map<String, Object>) response.get("data");
//            return mapToAnimeDto(data);
//
//        } catch (Exception e) {
//            log.error("Error getting anime by ID: {}", e.getMessage());
//            throw new ExternalApiException("Failed to get anime by ID", e);
//        }
//    }
//
//    private AnimeDto mapToAnimeDto(Map<String, Object> data) {
//        Integer malId = (Integer) data.get("mal_id");
//        String title = (String) data.get("title");
//        String synopsis = (String) data.get("synopsis");
//
//        Map<String, Object> studios = (Map<String, Object>) data.get("studios");
//        String studio = studios != null && !studios.isEmpty() ? (String) ((Map<String, Object>) studios.get(0)).get("name") : null;
//
//        List<Map<String, Object>> genres = (List<Map<String, Object>>) data.get("genres");
//        HashSet<String> tags = new HashSet<>();
//        if (genres != null) {
//            for (Map<String, Object> genre : genres) {
//                tags.add((String) genre.get("name"));
//            }
//        }
//
//        String aired = (String) ((Map<String, Object>) data.get("aired")).get("from");
//        LocalDate releaseDate = aired != null ? LocalDate.parse(aired) : null;
//
//        String imageUrl = (String) ((Map<String, Object>) ((Map<String, Object>) data.get("images")).get("jpg")).get("large_image_url");
//
//        Integer episodes = (Integer) data.get("episodes");
//
//        String status = (String) data.get("status");
//        Anime.AnimeStatus animeStatus;
//        if (status.contains("Airing")) {
//            animeStatus = Anime.AnimeStatus.AIRING;
//        } else if (status.contains("Finished")) {
//            animeStatus = Anime.AnimeStatus.FINISHED;
//        } else {
//            animeStatus = Anime.AnimeStatus.UPCOMING;
//        }
//
//        return AnimeDto.builder()
//                .title(title)
//                .description(synopsis)
//                .studio(studio)
//                .tags(tags)
//                .releaseDate(releaseDate)
//                .coverImage(imageUrl)
//                .externalId(malId.toString())
//                .externalApi(ViberMedia.ExternalApiType.JIKAN)
//                .episodes(episodes)
//                .status(animeStatus)
//                .build();
//    }
//}

package com.vibeverse.server.external.client;

import com.vibeverse.server.dto.AnimeDto;
import com.vibeverse.server.dto.MediaSearchResultDto;
import com.vibeverse.server.entity.Anime;
import com.vibeverse.server.entity.ViberMedia;
import com.vibeverse.server.exception.ExternalApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class JikanApiClient implements ExternalApiClient<AnimeDto> {

    private final RestTemplate restTemplate;

    @Value("${external.api.jikan.base-url}")
    private String baseUrl;

    @Override
    public MediaSearchResultDto<AnimeDto> search(String query, int page) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/anime")
                    .queryParam("q", query)
                    .queryParam("page", page)
                    .queryParam("limit", 20)
                    .toUriString();

            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null || !response.containsKey("data") || !response.containsKey("pagination")) {
                throw new ExternalApiException("Malformed response from Jikan API");
            }

            List<Map<String, Object>> data = (List<Map<String, Object>>) response.get("data");
            Map<String, Object> pagination = (Map<String, Object>) response.get("pagination");

            List<AnimeDto> results = new ArrayList<>();
            for (Map<String, Object> animeData : data) {
                results.add(mapToAnimeDto(animeData));
            }

            int currentPage = (Integer) pagination.getOrDefault("current_page", page);
            int lastPage = (Integer) pagination.getOrDefault("last_visible_page", page);

            int totalItems = 0;
            Object itemsObj = pagination.get("items");
            if (itemsObj instanceof Map) {
                Map<String, Object> items = (Map<String, Object>) itemsObj;
                totalItems = (Integer) items.getOrDefault("total", results.size());
            }

            return MediaSearchResultDto.<AnimeDto>builder()
                    .results(results)
                    .page(currentPage)
                    .totalPages(lastPage)
                    .totalResults(totalItems)
                    .build();

        } catch (Exception e) {
            log.error("Error searching anime: {}", e.getMessage(), e);
            throw new ExternalApiException("Failed to search anime", e);
        }
    }

    @Override
    public AnimeDto getById(String id) {
        try {
            String url = baseUrl + "/anime/" + id;
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null || !response.containsKey("data")) {
                throw new ExternalApiException("Failed to get anime details from Jikan API");
            }

            Map<String, Object> data = (Map<String, Object>) response.get("data");
            return mapToAnimeDto(data);

        } catch (Exception e) {
            log.error("Error getting anime by ID: {}", e.getMessage(), e);
            throw new ExternalApiException("Failed to get anime by ID", e);
        }
    }

    private AnimeDto mapToAnimeDto(Map<String, Object> data) {
        Integer malId = (Integer) data.get("mal_id");
        String title = (String) data.get("title");
        String synopsis = (String) data.get("synopsis");

        // Studios is a List<Map<String, Object>>
        String studio = null;
        Object studiosObj = data.get("studios");
        if (studiosObj instanceof List<?> studiosList && !studiosList.isEmpty()) {
            Object firstStudio = studiosList.get(0);
            if (firstStudio instanceof Map<?, ?> studioMap) {
                studio = (String) studioMap.get("name");
            }
        }

        List<Map<String, Object>> genres = (List<Map<String, Object>>) data.get("genres");
        Set<String> tags = new HashSet<>();
        if (genres != null) {
            for (Map<String, Object> genre : genres) {
                tags.add((String) genre.get("name"));
            }
        }

        LocalDate releaseDate = null;
        try {
            Object airedObj = data.get("aired");
            if (airedObj instanceof Map) {
                String aired = (String) ((Map<String, Object>) airedObj).get("from");
                if (aired != null) {
                    releaseDate = LocalDate.parse(aired.substring(0, 10)); // yyyy-MM-dd
                }
            }
        } catch (DateTimeParseException e) {
            log.warn("Could not parse anime release date", e);
        }

        String imageUrl = null;
        Object imagesObj = data.get("images");
        if (imagesObj instanceof Map<?, ?> imagesMap) {
            Object jpgObj = imagesMap.get("jpg");
            if (jpgObj instanceof Map<?, ?> jpgMap) {
                imageUrl = (String) jpgMap.get("large_image_url");
            }
        }

        Integer episodes = (Integer) data.get("episodes");
        String status = (String) data.get("status");

        Anime.AnimeStatus animeStatus;
        if (status != null && status.contains("Airing")) {
            animeStatus = Anime.AnimeStatus.AIRING;
        } else if (status != null && status.contains("Finished")) {
            animeStatus = Anime.AnimeStatus.FINISHED;
        } else {
            animeStatus = Anime.AnimeStatus.UPCOMING;
        }

        return AnimeDto.builder()
                .title(title)
                .description(synopsis)
                .studio(studio)
                .tags(tags)
                .releaseDate(releaseDate)
                .coverImage(imageUrl)
                .externalId(malId != null ? malId.toString() : null)
                .externalApi(ViberMedia.ExternalApiType.JIKAN)
                .episodes(episodes)
                .status(animeStatus)
                .build();
    }
}
