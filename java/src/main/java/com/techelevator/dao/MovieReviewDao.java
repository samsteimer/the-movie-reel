package com.techelevator.dao;

import com.techelevator.model.MovieReview;
import com.techelevator.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

public interface MovieReviewDao {


    MovieReview getMovieReviewById(int reviewId);

    List<MovieReview> getMovieReviewsById(int reviewId);

    List<MovieReview> getMovieReviewsByUser(int movieId, int userId);

    List<MovieReview> getMovieReviewByRating(int starRating);

    Integer createMovieReview(String review, int starRating, int movieId, int userId);



}
