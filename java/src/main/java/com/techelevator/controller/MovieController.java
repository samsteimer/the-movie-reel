package com.techelevator.controller;

import com.techelevator.dao.MovieDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Movie;
<<<<<<< HEAD
import com.techelevator.model.MovieReview;
=======
import com.techelevator.services.MovieService;
import org.springframework.http.HttpStatus;
>>>>>>> 61ac36638f1eef78ba87191aeb87b36141e57151
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/")


public class MovieController {

    private MovieDao movieDao;

    private UserDao userDao;

    private MovieService movieService;

    public MovieController(MovieDao movieDao, MovieService movieService, UserDao userDao) {
        this.movieDao = movieDao;
        this.movieService = movieService;
        this.userDao = userDao;
    }

    @GetMapping("movies/{id}")
    public Movie getMovieByMovieId(@Valid @PathVariable("id") Integer movieId) {
        return movieDao.getMovieByMovieId(movieId);
    }

    @GetMapping("movies/{title}")
    public Movie getMovieByTitle(@Valid @PathVariable("title") String title) {
        return movieDao.getMovieByTitle(title);
    }

    @GetMapping("movies/genre/{genre_id}")
    public List<Movie> getMoviesByGenreId(@Valid @PathVariable("genre_id") int genreId) {
        return movieDao.getMoviesByGenreId(genreId);
    }

    @GetMapping ("movies/lists/{list_id}")
    public List<Movie> getMoviesByListId(@Valid @PathVariable("list_id") int listId) {
        return movieDao.getMoviesByListId(listId);
    }

    @GetMapping ("movies/users/{user_id}")
    public List<Movie> getMoviesByUserId(@Valid @PathVariable("user_id") int userId) {
        return movieDao.getMoviesByUserId(userId);
    }

    @GetMapping ("movies/tmbd/{id}")
    public Movie getMovieFromApiId(@Valid @PathVariable("id") int id) {
        try {
            Movie movie = movieService.getMovieFromApiId(id);
            if (movie == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
            } else {
                return movie;
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not Available");
        }
    }

    @GetMapping ("movies/tmbd/search/{searchInput}")
    public Movie[] searchMoviesFromApi(@Valid @PathVariable("searchInput") String searchInput) {
        try {
            Movie[] movies = movieService.searchMoviesFromApi(searchInput);
            if (movies == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
            } else {
                return movies;
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not Available");
        }
    }

}
