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
public class JdbcMovieReviewDao implements MovieReviewDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcMovieReviewDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public MovieReview getMovieReviewById(int reviewId) {
        MovieReview review = null;
        // Provides the option to get a single movie review using the review id of the review.
        //TODO put in defensive code to account for null values
        try {
            String sql = "select review, star_rating, user_id, movie_id, review_id from reviews where review_id = ?;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, reviewId);
            if (results.next()) {
                review = mapRowToReview(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data Integrity violation", e);
        }
        return review;
    }

    @Override
    public List<MovieReview> getMovieReviewsById(int reviewId) {

        List<MovieReview> review = new ArrayList<>();
        // Provides the option to get several movie reviews using the review id of the review.
        //TODO put in defensive code to account for null values
        try {
            String sql = "select review, star_rating, user_id, movie_id, review_id from reviews where review_id = ?;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, reviewId);

            while (results.next()) {
                review.add(mapRowToReview(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return review;
    }

    @Override
    public List<MovieReview> getMovieReviewsByUser(int userId) {
        List<MovieReview> review = new ArrayList<>();
        // Provides a list of reviews for a specific movie from a specific user.
        //TODO put in defensive code to account for null values
        try {
            String sql = "select review, star_rating, user_id, movie_id, review_id from reviews where user_id = ?;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);

            while (results.next()) {
                review.add(mapRowToReview(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return review;
    }

    @Override
    public List<MovieReview> getMovieReviewByRating(int starRating) {
        List<MovieReview> review = new ArrayList<>();
        // Provides the ability to get a list of movie reviews based on the star rating of the reviews.
        //TODO put in defensive code to account for null values
        try {
            String sql = "select review, star_rating, user_id, movie_id, review_id from reviews where star_rating = ?;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, starRating);

            while (results.next()) {
                review.add(mapRowToReview(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the server or Database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return review;
    }

    @Override
    public MovieReview createMovieReview(MovieReview movieReview) {

        //TODO review text is a required field so make sure it required on the frontend.(This is to avoid null issues)
        String sql = "Insert Into reviews (review, star_rating, movie_id, user_id)" + "VALUES (?,?,?,?) RETURNING review_id;";

        Integer movieReviewId;
        try {
            movieReviewId = jdbcTemplate.queryForObject(sql, int.class, movieReview.getMovieReview(), movieReview.getStarRating(), movieReview.getMovieId(), movieReview.getUserId());
            if (movieReviewId == null) {
                throw new DaoException("Could not create movie review");
            }
            return getMovieReviewById(movieReviewId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the server or Database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public MovieReview getMovieReviewByMovieId(int movieId) {
        MovieReview review = null;
        // Provides the option to get a single movie review using a movie id.
        //TODO put in defensive code to account for null values
        try {
            String sql = "select review, star_rating, user_id, movie_id, review_id from reviews where movie_id = ?;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, movieId);

            if (results.next()) {
                review = mapRowToReview(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data Integrity violation", e);
        }
        return review;
    }

    @Override
    public List<MovieReview> getMovieReviewsByMovieId(int movieId) {

        List<MovieReview> review = new ArrayList<>();
        // Provides the option to get a list of movie reviews using a movie id.
        //TODO put in defensive code to account for null values
        try {
            String sql = "select review, star_rating, user_id, movie_id, review_id from reviews where movie_id = ?;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, movieId);

            while (results.next()) {
                review.add(mapRowToReview(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return review;
    }

    @Override
    public MovieReview getMovieReviewByUser(Integer movieId, int userId) {
        MovieReview review = null;
        // Provides the option to get a single movie review using a movie id.
        //TODO put in defensive code to account for null values
        try {
            String sql = "select review, star_rating, user_id, movie_id, review_id from reviews where movie_id = ? and user_Id = ?;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, movieId, userId);
            if (results.next()) {
                review = mapRowToReview(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data Integrity violation", e);
        }
        return review;
    }

    private MovieReview mapRowToReview(SqlRowSet rowSet) {
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
