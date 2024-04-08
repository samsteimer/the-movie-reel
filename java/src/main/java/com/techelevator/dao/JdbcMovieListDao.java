package com.techelevator.dao;

import com.techelevator.model.MovieList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JdbcMovieListDao implements MovieListDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MovieDao movieDao;

    @Override
    public MovieList getMovieListById(int id) {
        return null;
    }

    @Override
    public List<MovieList> getMovieListsById(int id) {
        return null;
    }

    @Override
    public List<MovieList> getMovieListsById(List<Integer> ids) {
        return null;
    }

    @Override
    public List<MovieList> getMovieListsByName(String name) {
        return null;
    }

    @Override
    public MovieList createMovieList(String name, String description) {
        return null;
    }

    @Override
    public MovieList updateMovieListById(int id, String name, String description) {
        return null;
    }

    public MovieList mapRowToMovieList(SqlRowSet result) {
        String name = result.getString("name");
        String description = result.getString("description");
    }
}
