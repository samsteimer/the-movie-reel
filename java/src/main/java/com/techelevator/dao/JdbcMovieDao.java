package com.techelevator.dao;

import com.techelevator.model.Movie;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcMovieDao implements MovieDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcMovieDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Movie getMovieByMovieId(Integer movieId) {
        Movie movie = null;

        String sql = "select * from movies where movie_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, movieId );

        if(results.next()) {
            movie = mapRowToMovie(results);
        }
        return movie;
    }

    @Override
    public Movie getMovieByTitle(String title) {
        Movie movie = null;
        String sql = "select * from movies where title = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, title);

        if(results.next()) {
            movie = mapRowToMovie(results);
        }

        return movie;
    }

    @Override
    public List<Movie> getMoviesByGenreId(int genreId) {
        List<Movie> moviesInGenre = new ArrayList<>();

        String sql = "select * from movies join movies_genres using (movie_id) where genre_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, genreId);

        while (results.next()) {
            Movie movie = mapRowToMovie(results);
            moviesInGenre.add(movie);
        }
        return moviesInGenre;
    }

    @Override
    public List<Movie> getMoviesByListId(int listId) {
        List<Movie> moviesInList = new ArrayList<>();

        String sql = "select * from movies join movies_lists using (movie_id) where list_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, listId);

        while (results.next()) {
            Movie movie = mapRowToMovie(results);
            moviesInList.add(movie);
        }
        return moviesInList;
    }

    @Override
    public List<Movie> getMoviesByUserId(int userId) {
        List<Movie> moviesInUserFav = new ArrayList<>();

        String sql = "select * from movies join users_movies using (movie_id) where user_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId );

        while (results.next()) {
            Movie movie = mapRowToMovie(results);
            moviesInUserFav.add(movie);
        }
        return moviesInUserFav;
    }

    public List<Movie> getMoviesByListId(List<Integer> listIds) {
        List<Movie> moviesInList = new ArrayList<>();

        String sql = "select * from movies join users_movies using (movie_id) where user_id in (?);";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, listIds);

        while (results.next()) {
            Movie movie = mapRowToMovie(results);
            moviesInList.add(movie);
        }
        return moviesInList;
    }

    public List<Movie> getMoviesByGenreId(List<Integer> genreIds) {
        List<Movie> moviesInGenre = new ArrayList<>();

        String sql = "select * from movies join movies_genres using (movie_id) where genre_id in (?);";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, genreIds);

        while (results.next()) {
            Movie movie = mapRowToMovie(results);
            moviesInGenre.add(movie);
        }
        return moviesInGenre;
    }





    public Movie mapRowToMovie(SqlRowSet rowSet) {
        Integer movieId = rowSet.getInt("movie_id");

        int apiMovieId = rowSet.getInt("api_movie_id");

        String title = rowSet.getString("title");

        String posterPath = rowSet.getString("poster_path");

        String backdropPath = rowSet.getString("backdrop_path");

        int imdbId = rowSet.getInt("imdb_id");

        String homepagePath = rowSet.getString("homepage");

        String overview = rowSet.getString("overview");

        Date releaseDateTemp = rowSet.getDate("release_date");
        LocalDate releaseDate = null;
        if(releaseDateTemp != null) {
            releaseDate = releaseDateTemp.toLocalDate();
        }

        int runtime = rowSet.getInt("runtime");

        Movie movie = new Movie();
        movie.setMovieId(movieId);
        movie.setApiMovieId(apiMovieId);
        movie.setTitle(title);
        movie.setPosterPath(posterPath);
        movie.setBackdropPath(backdropPath);
        movie.setImdbId(imdbId);
        movie.setHomepagePath(homepagePath);
        movie.setOverview(overview);
        movie.setReleaseDate(releaseDate);
        movie.setRuntime(runtime);

        return movie;




    }
}
