package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.MovieReview;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcMovieReviewDao implements MovieReviewDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcMovieReviewDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public MovieReview getMovieReviewById(int reviewId) {
        MovieReview review = null;

        try {
            String sql = "select review, star_rating, user_id, movie_id, review_id from review where review_id = ?;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, reviewId);
            review = mapRowToReview(results);
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data Integrity violation", e);
        }
        return review;
    }

    @Override
    public List<MovieReview> getMovieReviewsById(int reviewId) {

        List<MovieReview> review = new ArrayList<>();

        try {
            String sql = "select review, star_rating, user_id, movie_id, review_id from reviews where review_id = ?;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, reviewId);

            while (results.next()) {
                review.add(mapRowToReview(results));
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to the server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return review;
    }

    @Override
    public List<MovieReview> getMovieReviewsByUser(int movieId, int userId) {
        List<MovieReview> review = new ArrayList<>();

        try {
            String sql = "select review, star_rating, user_id, movie_id, review_id from reviews where movie_id = ? and userId = ?;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, movieId, userId);

            while (results.next()) {
                review.add(mapRowToReview(results));
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to the server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return review;
    }

    @Override
    public List<MovieReview> getMovieReviewByRating(int starRating) {
        List<MovieReview> review = new ArrayList<>();

        try {
            String sql = "select review, star_rating, user_id, movie_id, review_id from reviews where star_rating = ?;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, starRating);

            while (results.next()) {
                review.add(mapRowToReview(results));
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to the server or Database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return review;
    }

    @Override
    public Integer createMovieReview(String review, int starRating, int movieId, int userId) {
        MovieReview movieReview = null;

        String sql = "Insert Into review (review, star_rating, movie_id, user_id)" + "VALUES (?,?,?,?) RETURNING review_id;";

        Integer movieReviewId;
        try {
            movieReviewId = jdbcTemplate.queryForObject(sql, int.class, movieReview.getMovieReview(), movieReview.starRating, movieReview.movieId, movieReview.movieId, movieReview.getUserId());
                return movieReviewId;
            } catch (CannotGetJdbcConnectionException e) {
                 throw new DaoException("Unable to connect to the server or Database", e);
            } catch (DataIntegrityViolationException e) {
                 throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public MovieReview getMovieReviewByMovieId(int movieId) {
        MovieReview review = null;

        try {
            String sql = "select review, star_rating, user_id, movie_id, review_id from review where movie_id = ?;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, movieId);
            review = mapRowToReview(results);
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data Integrity violation", e);
        }
        return review;
    }

    @Override
    public List<MovieReview> getMovieReviewsByMovieId(int movieId) {

        List<MovieReview> review = new ArrayList<>();

        try {
            String sql = "select review, star_rating, user_id, movie_id, review_id from reviews where movie_id = ?;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, movieId);

            while (results.next()) {
                review.add(mapRowToReview(results));
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to the server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return review;
    }

    private MovieReview mapRowToReview(SqlRowSet rowSet){
        MovieReview review = new MovieReview();
        review.setMovieReview(rowSet.getString("review"));
        review.setMovieId(rowSet.getInt("movie_id"));
        review.setStarRating(rowSet.getInt("star_rating"));
        review.setUserId(rowSet.getInt("user_id"));
        review.setMovieId(rowSet.getInt("movie_id"));
        review.setReviewId(rowSet.getInt("review_id"));

        return review;
    }
}
