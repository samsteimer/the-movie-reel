package com.techelevator.dao;

import com.techelevator.model.MovieReview;

import java.util.List;


public interface MovieReviewDao {


    MovieReview getMovieReviewById(int reviewId);

    List<MovieReview> getMovieReviewsById(int reviewId);

    List<MovieReview> getMovieReviewsByUser(int userId);

    List<MovieReview> getMovieReviewByRating(int starRating);

    MovieReview createMovieReview(MovieReview movieReview);


    MovieReview getMovieReviewByMovieId(int movieId);

    List<MovieReview> getMovieReviewsByMovieId(int movieId);

    MovieReview getMovieReviewByUser(Integer movieId, int userId);
}
