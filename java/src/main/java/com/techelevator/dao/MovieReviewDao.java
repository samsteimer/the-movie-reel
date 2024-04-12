package com.techelevator.dao;

import com.techelevator.model.MovieReview;

import java.util.List;


public interface MovieReviewDao {


    MovieReview getMovieReviewById(int reviewId);

    List<MovieReview> getMovieReviewsById(int reviewId);

    List<MovieReview> getMovieReviewsByUser(int userId);

    List<MovieReview> getMovieReviewByRating(int starRating);

    Integer createMovieReview(String review, int starRating, int movieId, int userId);


    MovieReview getMovieReviewByMovieId(int movieId);

    List<MovieReview> getMovieReviewsByMovieId(int movieId);

    MovieReview getMovieReviewByUser(Integer movieId, int userId);
}
