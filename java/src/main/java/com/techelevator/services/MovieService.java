package com.techelevator.services;

import com.techelevator.model.Movie;
import com.techelevator.model.MovieApiDto;
import org.jboss.logging.BasicLogger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class MovieService {

    private static final String API_BASE_URL = "https://api.themoviedb.org/3/movie/";

    private static final String API_KEY = "d19868d1711164512199ca8d6fad28f0";

//    private final Movie movie;

    private final RestTemplate restTemplate = new RestTemplate();


    public MovieApiDto getMovieFromApiId(int apiId) {

        String url = API_BASE_URL + apiId + "?api_key=" + API_KEY;

        ResponseEntity<MovieApiDto> responseEntity = restTemplate.getForEntity(url, MovieApiDto.class);

        return responseEntity.getBody();
    }

    public MovieApiDto[] searchMoviesFromApi(String searchInput) {
        String url = "https://api.themoviedb.org/3/search/movie?query=" + searchInput + "&include_adult=false&language=en-US&page=1&api_key=" + API_KEY;
        return restTemplate.getForObject(url, MovieApiDto[].class);
    }


}
