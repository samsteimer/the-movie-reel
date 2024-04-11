package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.MovieList;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcMovieListDao implements MovieListDao {

    private JdbcTemplate jdbcTemplate;

    private MovieDao movieDao;

    public JdbcMovieListDao(JdbcTemplate jdbcTemplate, MovieDao movieDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.movieDao = movieDao;
    }

    @Override
    public MovieList getMovieListById(int id) {
        String sql = "select * from lists where list_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                return mapRowToMovieList(results);
            } else {
                return null;
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Could not connect.", e);
        }
    }

    @Override
    public List<MovieList> getMovieListsByName(String name) {
        List<MovieList> movieLists = new ArrayList<>();
        String sql = "select * from lists where list_name ilike ?";
        try {
            String pattern = "%" + name + "%";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, pattern);
            while (results.next()) {
                movieLists.add(mapRowToMovieList(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Could not connect.", e);
        }
        return movieLists;
    }

    @Override
    public MovieList createMovieList(MovieList movieList) {
        if (movieList == null) {
            throw new DaoException("List invalid");
        }

        if (getMovieListById(movieList.getListId()) != null) {
            throw new DaoException("List already exists");
        }

        String sql = "insert into lists (list_name, description) values (?,?) returning list_id;";
        try {
            Integer listId = jdbcTemplate.queryForObject(
                    sql, int.class, movieList.getListName(), movieList.getDescription());
            if (listId == null) {
                throw new DaoException("Could not create transfer");
            }
            return getMovieListById(listId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Could not connect.", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public MovieList updateMovieList(MovieList movieList) {
        if (movieList == null) {
            throw new DaoException("List invalid");
        }
        String sql = "update lists set list_name = ?, description = ? where list_id = ?";
        try {
            jdbcTemplate.update(sql, movieList.getListName(), movieList.getDescription(), movieList.getListId());
            return getMovieListById(movieList.getListId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Could not connect.", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public void addListMovie(int listId, int movieId) {
        String sql = "insert into movie_lists (list_id, movie_id) values (?, ?)";
        try {
            jdbcTemplate.update(sql, listId, movieId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Could not connect.", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public void removeListMovie(int listId, int movieId) {
        String sql = "delete from movie_lists where list_id = ? and movie_id = ?";
        try {
            jdbcTemplate.update(sql, listId, movieId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Could not connect.", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    public MovieList mapRowToMovieList(SqlRowSet result) {
        int id = result.getInt("list_id");
        String name = result.getString("list_name");
        String description = result.getString("description");

        var movieList = new MovieList();
        movieList.setListId(id);
        movieList.setListName(name);

        if (description != null) {
            movieList.setDescription(description);
        }

        return movieList;
    }
}
