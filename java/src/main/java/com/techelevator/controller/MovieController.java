package com.techelevator.controller;

import com.techelevator.dao.GenreDao;
import com.techelevator.dao.MovieDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.*;
import com.techelevator.services.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/movie_info")
public class MovieController {

    private MovieDao movieDao;

    private UserDao userDao;

    private MovieService movieService;

    private GenreDao genreDao;

    public MovieController(MovieDao movieDao, MovieService movieService, UserDao userDao, GenreDao genreDao) {
        this.movieDao = movieDao;
        this.movieService = movieService;
        this.userDao = userDao;
        this.genreDao = genreDao;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Movie createMovie(@Valid @RequestBody Movie movie) {
//        try {
        return movieDao.createMovie(movie);
//        } catch (DaoException e) {
//            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service unavailable");
//        }
    }

    @PostMapping("/addgenre")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Genre> addGenreToMovie(@Valid @RequestBody Movie movie) {
        List<Genre> genres = movie.getGenres();
        List<Genre> fetchedGenres = new ArrayList<>();

        // Fetch genres by ID from GenreDao if needed
        for (Genre genre : genres) {
            // Assuming getGenreById returns null if the genre does not exist
            Genre fetchedGenre = genreDao.getGenreById(genre.getGenreId());
            if (fetchedGenre != null) {
                fetchedGenres.add(fetchedGenre);
            }
        }

        // If any genres were successfully fetched, add them to the movie
        if (!fetchedGenres.isEmpty()) {
            int movieId = movie.getMovieId(); // Assuming you have the movie ID
            for (Genre genre : fetchedGenres) {
                int genreId = genre.getGenreId();
                movieDao.addMovieGenre(genreId, movieId);
            }
        }

        return fetchedGenres;
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

    @DeleteMapping("/{movieId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteMovie(@PathVariable int movieId) {
        Movie movie = movieDao.getMovieByMovieId(movieId);
        if (movie == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }
        try {
            movieDao.deleteMovieById(movieId);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service unavailable");
        }
    }

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

    @GetMapping("/genre")
    public List<Movie> getMoviesByGenreIds(@RequestParam String genreIds) {
        if (genreIds.equals("")) {
            return new ArrayList<>();
        }
        try {
            List<Integer> genreIdList = Arrays
                    .stream(genreIds.split(","))
                    .map(str -> Integer.valueOf(str)).collect(Collectors.toList());
            if (genreIdList == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre list invalid.");
            }
            List<Movie> movies = movieDao.getMoviesByGenreId(genreIdList);
            if (movies == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movies not found.");
            } else {
                return movies;
            }
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Genres not found");
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

    @GetMapping("/tmdb/{api_movie_id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

    @GetMapping("/tmdb/search/{input}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public MovieListApiDto searchMoviesFromApi(@Valid @PathVariable("input") String input) {
        try {
            MovieListApiDto movies = movieService.searchMoviesFromApi(input);
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
