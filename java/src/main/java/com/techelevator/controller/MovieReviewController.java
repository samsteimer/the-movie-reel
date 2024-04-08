package com.techelevator.controller;

import com.techelevator.dao.MovieDao;
import com.techelevator.dao.MovieReviewDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Movie;
import com.techelevator.model.MovieReview;
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


public class MovieReviewController {

    private MovieReviewDao movieReviewDao;

    private UserDao userDao;

    public MovieReviewController(MovieReviewDao movieReviewDao, UserDao userDao) {
        this.movieReviewDao = movieReviewDao;
        this.userDao = userDao;
    }

    @GetMapping("movieReview/{reviewId}")
    public MovieReview getMovieReviewById(@Valid @PathVariable("reviewId") Integer reviewId) {
        return movieReviewDao.getMovieReviewById(reviewId);
    }

    @GetMapping("movieReview/{reviewId}")
    public List<MovieReview> getMovieReviewsById(@Valid @PathVariable("reviewId") Integer reviewId) {
        return movieReviewDao.getMovieReviewsById(reviewId);
    }

    @GetMapping("movieReview/{movieId}/{userId}")
    public List<MovieReview> getMovieReviewsByUser(@Valid @PathVariable("movieId, userId") Integer movieId, Integer userId) {
        return movieReviewDao.getMovieReviewsByUser(movieId, userId);
    }

    @GetMapping("movieReview/{starRating}")
    public List<MovieReview> getMovieReviewByRating(@Valid @PathVariable("starRating") Integer starRating) {
        return movieReviewDao.getMovieReviewByRating(starRating);
    }
}
