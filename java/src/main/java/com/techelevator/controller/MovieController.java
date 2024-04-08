package com.techelevator.controller;

import com.techelevator.dao.MovieDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Movie;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/")


public class MovieController {

    private MovieDao movieDao;

    private UserDao userDao;

    public MovieController(MovieDao movieDao, UserDao userDao) {
        this.movieDao = movieDao;
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



}
