package com.techelevator.controller;

import com.techelevator.dao.MovieDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Movie;
import com.techelevator.model.MovieList;
import com.techelevator.services.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/movie_info")
public class MovieController {

    private MovieDao movieDao;

    private UserDao userDao;

    private MovieService movieService;

    public MovieController(MovieDao movieDao, MovieService movieService, UserDao userDao) {
        this.movieDao = movieDao;
        this.movieService = movieService;
        this.userDao = userDao;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Movie createMovie(@Valid @RequestBody Movie movie) {
        try {
            return movieDao.createMovie(movie);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service unavailable");
        }
    }

//    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public Movie updateMovie(@Valid @RequestBody Movie movie, @PathVariable int movieId) {
//        if (movie.getMovieId() != movieId) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Non-matching movie IDs");
//        }
//        try {
//            return movieDao.updateMovie(movie);
//        } catch (DaoException e) {
//            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service unavailable");
//        }
//    }

    @GetMapping("/{id}")
    public Movie getMovieByMovieId(@Valid @PathVariable("id") Integer movieId) {
        try {
            Movie movie = movieDao.getMovieByMovieId(movieId);
            if (movie == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
            } else {
                return movie;
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not Available");
        }
    }

//    @GetMapping("/{title}")
//    public Movie getMovieByTitle(@Valid @PathVariable("title") String title) {
//        try {
//            Movie movie = movieDao.getMovieByTitle(title);
//            if (movie == null) {
//                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
//            } else {
//                return movie;
//            }
//        } catch (DaoException e) {
//            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not Available");
//        }
//    }

    @GetMapping("/genre/{genre_id}")
    public List<Movie> getMoviesByGenreId(@Valid @PathVariable("genre_id") int genreId) {
        try {
            List<Movie> movies = movieDao.getMoviesByGenreId(genreId);
            if (movies == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movies not found.");
            } else {
                return movies;
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not Available");
        }
    }

    @GetMapping("/lists/{list_id}")
    public List<Movie> getMoviesByListId(@Valid @PathVariable("list_id") int listId) {
        try {
            List<Movie> movies = movieDao.getMoviesByListId(listId);
            if (movies == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movies not found.");
            } else {
                return movies;
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not Available");
        }
    }

// Using /favorites
//    @GetMapping ("movies/users/{user_id}")
//    public List<Movie> getMoviesByUserId(@Valid @PathVariable("user_id") int userId) {
//        return movieDao.getMoviesByUserId(userId);
//    }

    @GetMapping("/tmbd/{api_movie_id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public MovieApiDto getMovieFromApiId(@Valid @PathVariable("api_movie_id") int id) {
        try {
            MovieApiDto movie = movieService.getMovieFromApiId(id);
            if (movie == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
            } else {
                return movie;
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not Available");
        }
    }

    @GetMapping("/tmbd/search")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public MovieApiDto[] searchMoviesFromApi(@Valid @RequestParam String search) {
        try {
            MovieApiDto[] movies = movieService.searchMoviesFromApi(search);
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
