package com.techelevator.controller;

import com.techelevator.dao.MovieDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Movie;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class UserController {

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private UserDao userDao;

    @PutMapping("/profile")
    public User updateUser(User user, Principal principal) {
        if (userDao.getUserByPrincipal(principal).getId() != user.getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied.");
        }
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not found.");
        }
        try {
            userDao.updateUser(user);
            return userDao.getUserById(user.getId());
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not Available");
        }
    }

    @GetMapping("/favorites")
    public List<Movie> getFavoriteMovies(Principal principal) {
        User user = userDao.getUserByPrincipal(principal);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not found.");
        }
        try {
            List<Movie> movies = movieDao.getMoviesByUserId(user.getId());
            if (movies == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Favorites not found.");
            } else {
                return movies;
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not Available");
        }
    }

    @PutMapping("/favorites/{movieId}")
    public Movie addFavoriteMovie(@RequestParam int movieId, Principal principal) {
        User user = userDao.getUserByPrincipal(principal);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not found.");
        }
        try {
            Movie movie = movieDao.getMovieByMovieId(movieId);
            if (movie == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found.");
            } else {
                userDao.addFavoriteMovie(user.getId(), movie.getMovieId());
                return movie;
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not Available");
        }
    }

    @DeleteMapping("/favorites/{movieId}")
    public void removeFavoriteMovie(@RequestParam int movieId, Principal principal) {
        User user = userDao.getUserByPrincipal(principal);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not found.");
        }
        try {
            Movie movie = movieDao.getMovieByMovieId(movieId);
            if (movie == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found.");
            } else {
                userDao.removeFavoriteMovie(user.getId(), movie.getMovieId());
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not Available");
        }
    }
}
