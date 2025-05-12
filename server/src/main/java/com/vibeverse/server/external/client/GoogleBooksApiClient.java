package com.vibeverse.server.external.client;

import com.vibeverse.server.dto.BookDto;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class GoogleBooksApiClient implements ExternalApiClient<BookDto> {

    private final RestTemplate restTemplate;
    
    @Value("${external.api.google-books.base-url}")
    private String baseUrl;
    
    @Value("${external.api.google-books.api-key}")
    private String apiKey;
    
    @Override
    public MediaSearchResultDto<BookDto> search(String query, int page) {
        try {
            int startIndex = (page - 1) * 20;
            
            String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/volumes")
                    .queryParam("q", query)
                    .queryParam("startIndex", startIndex)
                    .queryParam("maxResults", 20)
                    .queryParam("key", apiKey)
                    .toUriString();
            
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            
            if (response == null) {
                throw new ExternalApiException("Failed to get response from Google Books API");
            }
            
            List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");
            Integer totalItems = (Integer) response.get("totalItems");
            
            List<BookDto> bookResults = new ArrayList<>();
            
            if (items != null) {
                for (Map<String, Object> item : items) {
                    bookResults.add(mapToBookDto(item));
                }
            }
            
            int totalPages = (int) Math.ceil((double) totalItems / 20);
            
            return MediaSearchResultDto.<BookDto>builder()
                    .results(bookResults)
                    .page(page)
                    .totalPages(totalPages)
                    .totalResults(totalItems)
                    .build();
            
        } catch (Exception e) {
            log.error("Error searching books: {}", e.getMessage());
            throw new ExternalApiException("Failed to search books", e);
        }
    }
    
    @Override
    public BookDto getById(String id) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/volumes/" + id)
                    .queryParam("key", apiKey)
                    .toUriString();
            
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            
            if (response == null) {
                throw new ExternalApiException("Failed to get response from Google Books API");
            }
            
            return mapToBookDto(response);
            
        } catch (Exception e) {
            log.error("Error getting book by ID: {}", e.getMessage());
            throw new ExternalApiException("Failed to get book by ID", e);
        }
    }
    
    private BookDto mapToBookDto(Map<String, Object> data) {
        String id = (String) data.get("id");
        Map<String, Object> volumeInfo = (Map<String, Object>) data.get("volumeInfo");
        
        String title = (String) volumeInfo.get("title");
        String description = (String) volumeInfo.get("description");
        
        // Author
        String author = null;
        if (volumeInfo.containsKey("authors")) {
            List<String> authors = (List<String>) volumeInfo.get("authors");
            if (authors != null && !authors.isEmpty()) {
                author = authors.get(0);
            }
        }
        
        // ISBN
        String isbn = null;
        if (volumeInfo.containsKey("industryIdentifiers")) {
            List<Map<String, Object>> identifiers = (List<Map<String, Object>>) volumeInfo.get("industryIdentifiers");
            if (identifiers != null) {
                for (Map<String, Object> identifier : identifiers) {
                    String type = (String) identifier.get("type");
                    if ("ISBN_13".equals(type) || "ISBN_10".equals(type)) {
                        isbn = (String) identifier.get("identifier");
                        break;
                    }
                }
            }
        }
        
        // Publish date
        LocalDate publishDate = null;
        if (volumeInfo.containsKey("publishedDate")) {
            String dateStr = (String) volumeInfo.get("publishedDate");
            if (dateStr != null && !dateStr.isEmpty()) {
                try {
                    if (dateStr.length() == 4) {
                        publishDate = LocalDate.of(Integer.parseInt(dateStr), 1, 1);
                    } else if (dateStr.length() == 7) {
                        publishDate = LocalDate.parse(dateStr + "-01", DateTimeFormatter.ISO_DATE);
                    } else {
                        publishDate = LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE);
                    }
                } catch (Exception e) {
                    log.warn("Could not parse publish date: {}", dateStr);
                }
            }
        }
        
        // Genres
        HashSet<String> genres = new HashSet<>();
        if (volumeInfo.containsKey("categories")) {
            List<String> categories = (List<String>) volumeInfo.get("categories");
            if (categories != null) {
                genres.addAll(categories);
            }
        }
        
        // Cover image
        String imageUrl = null;
        if (volumeInfo.containsKey("imageLinks")) {
            Map<String, Object> imageLinks = (Map<String, Object>) volumeInfo.get("imageLinks");
            if (imageLinks != null) {
                imageUrl = (String) imageLinks.get("thumbnail");
                // Convert to https if needed
                if (imageUrl != null && imageUrl.startsWith("http:")) {
                    imageUrl = "https" + imageUrl.substring(4);
                }
            }
        }
        
        // Page count
        Integer pageCount = volumeInfo.containsKey("pageCount") ? (Integer) volumeInfo.get("pageCount") : null;
        
        // Publisher
        String publisher = (String) volumeInfo.get("publisher");
        
        return BookDto.builder()
                .title(title)
                .description(description)
                .author(author)
                .isbn(isbn)
                .publishDate(publishDate)
                .genres(genres)
                .coverImage(imageUrl)
                .externalId(id)
                .externalApi(ViberMedia.ExternalApiType.GOOGLE_BOOKS)
                .pageCount(pageCount)
                .publisher(publisher)
                .build();
    }
}
