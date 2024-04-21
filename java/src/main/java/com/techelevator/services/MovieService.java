package com.techelevator.services;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.model.MovieListApiDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.techelevator.model.Movie;
import com.techelevator.model.MovieApiDto;
import org.jboss.logging.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


import java.io.IOException;
import java.util.List;


@Component
public class MovieService {

    private static final String API_BASE_URL = "https://api.themoviedb.org/3/movie/";

    private static final String API_KEY = "d19868d1711164512199ca8d6fad28f0";


    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();


    public MovieApiDto getMovieFromApiId(int apiId) {

        String url = API_BASE_URL + apiId + "?api_key=" + API_KEY;

        return restTemplate.getForObject(url, MovieApiDto.class);
    }


    public MovieListApiDto searchMoviesFromApi(String searchInput) {
        String url = "https://api.themoviedb.org/3/search/movie?query=" + searchInput + "&include_adult=false&language=en-US&page=1&api_key=" + API_KEY;
        return restTemplate.getForObject(url, MovieListApiDto.class);
    }


}
