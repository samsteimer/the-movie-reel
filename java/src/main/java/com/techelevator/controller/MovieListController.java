package com.techelevator.controller;

import com.techelevator.dao.MovieDao;
import com.techelevator.dao.MovieListDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Movie;
import com.techelevator.model.MovieList;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/lists")
public class MovieListController {

    private MovieListDao movieListDao;

    private MovieDao movieDao;

    public MovieListController(MovieListDao movieListDao, MovieDao movieDao) {
        this.movieListDao = movieListDao;
        this.movieDao = movieDao;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public MovieList createMovieList(@Valid @RequestBody MovieList movieList) {
        try {
            return movieListDao.createMovieList(movieList);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service unavailable");
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public MovieList updateMovieList(@Valid @RequestBody MovieList movieList, @PathVariable int movieListId) {
        if (movieList.getListId() != movieListId) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Non-matching movie list IDs");
        }
        try {
            return movieListDao.updateMovieList(movieList);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service unavailable");
        }
    }

    @GetMapping
    public List<MovieList> getMovieListsByName(@RequestParam String name) {
        try {
            List<MovieList> movieLists = movieListDao.getMovieListsByName(name);
            if (movieLists == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "List not found.");
            } else {
                return movieLists;
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not Available");
        }
    }

    @GetMapping("/{id}")
    public MovieList getMovieList(@PathVariable int id) {
        try {
            MovieList movieList = movieListDao.getMovieListById(id);
            if (movieList == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "List not found.");
            } else {
                return movieList;
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not Available");
        }
    }

    @PutMapping("/{id}/movies/{movieId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Movie addListMovie(@PathVariable int listId, @PathVariable int movieId) {
        MovieList movieList = movieListDao.getMovieListById(listId);
        if (movieList == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "List not found.");
        }
        try {
            Movie movie = movieDao.getMovieByMovieId(movieId);
            if (movie == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found.");
            } else {
                movieListDao.addListMovie(movieList.getListId(), movie.getMovieId());
                return movie;
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not Available");
        }
    }

    @DeleteMapping("/{id}/movies/{movieId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void removeListMovie(@PathVariable int listId, @PathVariable int movieId) {
        MovieList movieList = movieListDao.getMovieListById(listId);
        if (movieList == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "List not found.");
        }
        try {
            Movie movie = movieDao.getMovieByMovieId(movieId);
            if (movie == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found.");
            } else {
                movieListDao.removeListMovie(movieList.getListId(), movie.getMovieId());
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not Available");
        }
    }
}
