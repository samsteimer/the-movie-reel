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

import javax.validation.Valid;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/movieReview")


public class MovieReviewController {

    private MovieReviewDao movieReviewDao;

    private UserDao userDao;

    public MovieReviewController(MovieReviewDao movieReviewDao, UserDao userDao) {
        this.movieReviewDao = movieReviewDao;
        this.userDao = userDao;
    }

    @GetMapping("movieReview/{reviewId}")
    public MovieReview getMovieReviewById(@Valid @PathVariable("reviewId") Integer reviewId) {
        try {
            MovieReview movieReview = movieReviewDao.getMovieReviewById(reviewId);
            if (movieReview == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie review not found");
            } else {
                return movieReview;
            }
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not available");
        }
    }

    @GetMapping("movieReview/{reviewId}")
    public List<MovieReview> getMovieReviewsById(@Valid @PathVariable("reviewId") Integer reviewId) {
        try {
            List<MovieReview> movieReview =  movieReviewDao.getMovieReviewsById(reviewId);
            if (movieReview == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie review not found");
            } else {
                return movieReview;
            }
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not available" );
        }
    }

    @GetMapping("movieReview/{movieId}/{userId}")
    public List<MovieReview> getMovieReviewsByUser(@Valid @PathVariable("movieId, userId") Integer movieId, Integer userId) {
       try{
           List<MovieReview> movieReview = movieReviewDao.getMovieReviewsByUser(movieId, userId);
           if(movieReview == null){
               throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie review not found");
           } else {
               return movieReview;
           }
       }catch (DaoException e){
           throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not available");
       }
    }

    @GetMapping("movieReview/{starRating}")
    public List<MovieReview> getMovieReviewByRating(@Valid @PathVariable("starRating") Integer starRating) {
        try{
            List<MovieReview> movieReview = movieReviewDao.getMovieReviewByRating(starRating);
            if(movieReview == null){
                throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie review not found");
            } else {
                return movieReview;
            }
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not available");
        }
    }

}
