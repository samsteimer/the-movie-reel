package com.techelevator.dao;

import com.techelevator.model.MovieReview;

import java.util.List;

public interface MovieReviewDao {


    MovieReview getMovieReviewById(int reviewId);

    List<MovieReview> getMovieReviewsById(int reviewId);

    List<MovieReview> getMovieReviewsByUser(int movieId, int userId);

    List<MovieReview> getMovieReviewByRating(int starRating);

}
