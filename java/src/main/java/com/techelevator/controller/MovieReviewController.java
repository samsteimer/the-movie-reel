package com.techelevator.controller;

import com.techelevator.dao.MovieDao;
import com.techelevator.dao.MovieReviewDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Movie;
import com.techelevator.model.MovieReview;
import com.techelevator.model.RegisterUserDto;
import com.techelevator.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
//@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/movies")
public class MovieReviewController {

    private final MovieReviewDao movieReviewDao;

    private final UserDao userDao;

    public MovieReviewController(MovieReviewDao movieReviewDao, UserDao userDao) {
        this.movieReviewDao = movieReviewDao;
        this.userDao = userDao;
    }

    @GetMapping("/review/{reviewId}")
    public MovieReview getMovieReviewById(@PathVariable("reviewId") Integer reviewId) {
        try {
            MovieReview movieReview = movieReviewDao.getMovieReviewById(reviewId);
            if (movieReview == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie review not found");
            } else {
                return movieReview;
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not available");
        }
    }

    //TODO turn this into a request param since you are getting all of the items.
    @GetMapping("/{movieId}/reviews")
    public MovieReview getMovieReviewByMovieId(@PathVariable("movieId") Integer movieId) {
        try {
            MovieReview movieReview = movieReviewDao.getMovieReviewByMovieId(movieId);
            if (movieReview == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie review not found");
            } else {
                return movieReview;
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not available");
        }
    }

    @GetMapping("/reviews")
    public List<MovieReview> getMovieReviewsByUser(@RequestParam String userId) {
        try {
            List<MovieReview> movieReview = movieReviewDao.getMovieReviewsByUser(Integer.parseInt(userId));
            if (movieReview == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie review not found");
            } else {
                return movieReview;
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not available");
        }
    }

    @GetMapping("/{starRating}/starRating")
    public List<MovieReview> getMovieReviewByRating(@PathVariable("starRating") Integer starRating) {
        try {
            List<MovieReview> movieReview = movieReviewDao.getMovieReviewByRating(starRating);
            if (movieReview == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie review not found");
            } else {
                return movieReview;
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not available");
        }
    }

    @GetMapping("/{movieId}/reviews/{userId}")
    public MovieReview getMovieReviewByUser(@PathVariable("movieId") Integer movieId, @PathVariable("userId") Integer userId) {
        try {
            MovieReview movieReview = movieReviewDao.getMovieReviewByUser(movieId, userId);
            if (movieReview == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie review not found");
            } else {
                return movieReview;
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not available");
        }
    }

    //TODO create a mapping for the create review

}
