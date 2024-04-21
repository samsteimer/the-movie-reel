package com.techelevator.controller;

import com.techelevator.dao.MovieDao;
import com.techelevator.dao.MovieReviewDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
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
    @GetMapping("/{movieId}/MovieReview")
    public MovieReview getMovieReviewForEditByMovieId(@PathVariable("movieId") Integer movieId) {
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

//TODO continue setting this up for returning multiple movies.
    @GetMapping("/{movieId}/reviews")
    public List<MovieReview> getMovieReviewsByMovieId(@PathVariable("movieId") Integer movieId) {
        try {
            List<MovieReview> movieReviews = movieReviewDao.getMovieReviewsByMovieId(movieId);
            if (movieReviews == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie review not found");
            } else {
                return movieReviews;
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

    @PostMapping("/reviews")
    @PreAuthorize("isAuthenticated()")
    public MovieReview createMovieReview(@RequestBody MovieReview movieReview, Principal principal) {
        User user = userDao.getUserByPrincipal(principal);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not found.");
        }
        if (user.getId() != movieReview.getUserId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied.");
        }
        try {
            return movieReviewDao.createMovieReview(movieReview);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service unavailable");
        }
    }

}
