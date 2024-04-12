package com.techelevator.controller;

import com.techelevator.dao.GenreDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Genre;
import com.techelevator.model.Genre;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/genre")
public class GenreController {

    private GenreDao genreDao;

    public GenreController(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @GetMapping("/{id}")
    Genre getGenreById(@PathVariable int genreId) {
        try {
            Genre genre = genreDao.getGenreById(genreId);
            if (genre == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found");
            } else {
                return genre;
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not Available");
        }
    }

    @GetMapping
    List<Genre> getGenres() {
        try {
            List<Genre> genres = genreDao.getListOfGenres();
            if (genres == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Genres not found.");
            } else {
                return genres;
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not Available");
        }
    }
}
