package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Movie;
import com.techelevator.model.MovieList;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcMovieDao implements MovieDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcMovieDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Movie getMovieByMovieId(Integer movieId) {
        Movie movie = null;

        String sql = "select * from movies where movie_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, movieId);

        if (results.next()) {
            movie = mapRowToMovie(results);
        }
        return movie;
    }

    @Override
    public Movie getMovieByTitle(String title) {
        Movie movie = null;
        String sql = "select * from movies where title = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, title);

        if (results.next()) {
            movie = mapRowToMovie(results);
        }

        return movie;
    }

    @Override
    public List<Movie> getMoviesByGenreId(int genreId) {
        List<Movie> moviesInGenre = new ArrayList<>();

        String sql = "select * from movies join movies_genres using (movie_id) where genre_id = ?;";
        try {
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, genreId);

        while (results.next()) {
            Movie movie = mapRowToMovie(results);
            moviesInGenre.add(movie);
        }
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to access server or database");
        }
        return moviesInGenre;
    }

    @Override
    public List<Movie> getMoviesByListId(int listId) {
        List<Movie> moviesInList = new ArrayList<>();

        String sql = "select * from movies join movies_lists using (movie_id) where list_id = ?;";
        try {
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, listId);

        while (results.next()) {
            Movie movie = mapRowToMovie(results);
            moviesInList.add(movie);
        }
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to access server or database");
        }
        return moviesInList;
    }

    @Override
    public List<Movie> getMoviesByUserId(int userId) {
        List<Movie> moviesInUserFav = new ArrayList<>();

        String sql = "select * from movies join users_movies using (movie_id) where user_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);

        while (results.next()) {
            Movie movie = mapRowToMovie(results);
            moviesInUserFav.add(movie);
        }
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to access server or database");
        }
        return moviesInUserFav;
    }

    public List<Movie> getMoviesByListId(List<Integer> listIds) {
        List<Movie> moviesInList = new ArrayList<>();

        String sql = "select * from movies join users_movies using (movie_id) where user_id in (?);";
        try {
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, listIds);

        while (results.next()) {
            Movie movie = mapRowToMovie(results);
            moviesInList.add(movie);
        }
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to access server or database");
        }
        return moviesInList;
    }

    public List<Movie> getMoviesByGenreId(List<Integer> genreIds) {
        List<Movie> moviesInGenre = new ArrayList<>();

        String sql = "select * from movies join movies_genres using (movie_id) where genre_id in (?);";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, genreIds);

            while (results.next()) {
                Movie movie = mapRowToMovie(results);
                moviesInGenre.add(movie);
            }
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to access server or database");
        }
        return moviesInGenre;
    }

    @Override
    public Movie createMovie(Movie movie) {
        if (movie == null) {
            throw new DaoException("Movie invalid");
        }

        if (getMovieByMovieId(movie.getMovieId()) != null) {
            throw new DaoException("Movie already exists");
        }

        String sql = "insert into movies (movie_id, api_movie_id, title, poster_path, backdrop_path, imbd_id, homepage, overview, release_date, runtime) " +
                "values (?,?,?,?,?,?,?,?,?,?) returning movie_id";
        try {
            Integer movieId = jdbcTemplate.queryForObject(
                    sql, int.class,
                    movie.getMovieId(),
                    movie.getApiMovieId(),
                    movie.getTitle(),
                    movie.getPosterPath(),
                    movie.getBackdropPath(),
                    movie.getImdbId(),
                    movie.getHomepage(),
                    movie.getOverview(),
                    movie.getRuntime());
            if (movieId == null) {
                throw new DaoException("Could not create transfer");
            }
            return getMovieByMovieId(movieId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Could not connect.", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public Movie updateMovie(Movie movie) {
        if (movie == null) {
            throw new DaoException("Movie invalid");
        }
        String sql = "update movies set movie_id = ?, api_movie_id = ?, title = ?, poster_path = ?, backdrop_path = ?, " +
                "imbd_id = ?, homepage = ?, overview = ?, release_date = ?, runtime = ? " +
                "where movie_id = ?";
        try {
            jdbcTemplate.update(sql,
                    movie.getMovieId(),
                    movie.getApiMovieId(),
                    movie.getTitle(),
                    movie.getPosterPath(),
                    movie.getBackdropPath(),
                    movie.getImdbId(),
                    movie.getHomepage(),
                    movie.getOverview(),
                    movie.getRuntime(),
                    movie.getMovieId());
            return getMovieByMovieId(movie.getMovieId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Could not connect.", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public void addMovieGenre(int genreId, int movieId) {
        String sql = "insert into movie_genres (genre_id, movie_id) values (?, ?)";
        try {
            jdbcTemplate.update(sql, genreId, movieId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Could not connect.", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public void removeMovieGenre(int genreId, int movieId) {
        String sql = "delete from movie_genres where genre_id = ? and movie_id = ?";
        try {
            jdbcTemplate.update(sql, genreId, movieId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Could not connect.", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    public Movie mapRowToMovie(SqlRowSet rowSet) {
        Integer movieId = rowSet.getInt("movie_id");
        int apiMovieId = rowSet.getInt("api_movie_id");
        String title = rowSet.getString("title");
        String posterPath = rowSet.getString("poster_path");
        String backdropPath = rowSet.getString("backdrop_path");
        int imdbId = rowSet.getInt("imdb_id");
        String homepage = rowSet.getString("homepage");
        String overview = rowSet.getString("overview");
        Date releaseDateTemp = rowSet.getDate("release_date");
        LocalDate releaseDate = null;
        if (releaseDateTemp != null) {
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
        movie.setHomepage(homepage);
        movie.setOverview(overview);
        movie.setReleaseDate(releaseDate);
        movie.setRuntime(runtime);

        return movie;
    }
}
