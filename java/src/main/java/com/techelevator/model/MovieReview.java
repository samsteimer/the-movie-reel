package com.techelevator.model;

public class MovieReview {

    public String movieReview;
    public int starRating;
    public int movieId;
    public int userId;

    public MovieReview(){}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int reviewId;

    public String getMovieReview() {
        return movieReview;
    }

    public void setMovieReview(String movieReview) {
        this.movieReview = movieReview;
    }

    public int getStarRating() {
        return starRating;
    }

    public void setStarRating(int starRating) {
        this.starRating = starRating;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int id) {
        movieId = movieId;
    }

    public MovieReview(String movieReview, int starRating, int movieId, int userId, int reviewId) {
        this.movieReview = movieReview;
        this.starRating = starRating;
        this.movieId = movieId;
        this.userId = userId;
        this.reviewId = reviewId;
    }
}
