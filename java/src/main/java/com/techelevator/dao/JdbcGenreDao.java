package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Genre;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcGenreDao implements GenreDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcGenreDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre getGenreById(int genreId) {
        Genre genre = null;

        String sql = "select * from genres where genre_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, genreId);

            if (results.next()) {
                genre = mapRowToGenre(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return genre;
    }


    @Override
    public List<Genre> getListOfGenres() {
        List<Genre> listOfGenres = new ArrayList<>();

        String sql = "select * from genres";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

            while (results.next()) {
                Genre genre = mapRowToGenre(results);
                listOfGenres.add(genre);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return listOfGenres;
    }

    @Override
    public Genre getGenreByName(String name) {
        Genre genre = null;

        String sql = "select * from genres where genre_name ilike ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, name);

            if (results.next()) {
                genre = mapRowToGenre(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return genre;
    }

    @Override
    public List<Genre> getGenresByMovieId(int movieId) {
        List<Genre> listOfGenres = new ArrayList<>();

        String sql = "select * from genres join movies_genres using (genre_id) where movie_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, movieId);

            while (results.next()) {
                Genre genre = mapRowToGenre(results);
                listOfGenres.add(genre);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return listOfGenres;
    }

    @Override
    public List<Genre> getGenresByUserId(int userId) {
        List<Genre> listOfGenres = new ArrayList<>();

        String sql = "select * from genres join users_genres using (genre_id) where user_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);

            while (results.next()) {
                Genre genre = mapRowToGenre(results);
                listOfGenres.add(genre);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return listOfGenres;
    }

    public Genre mapRowToGenre(SqlRowSet rowSet) {
        int genreId = rowSet.getInt("genre_id");
        String genreName = rowSet.getString("genre_name");

        Genre genre = new Genre();
        genre.setGenreId(genreId);
        genre.setGenreName(genreName);

        return genre;
    }
}
