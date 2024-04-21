package com.techelevator.dao;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Genre;
import com.techelevator.model.RegisterUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.techelevator.model.User;

@Component
public class JdbcUserDao implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private GenreDao genreDao;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getUserById(int userId) {
        User user = null;
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            if (results.next()) {
                user = mapRowToUser(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return user;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                User user = mapRowToUser(results);
                users.add(user);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return users;
    }

    @Override
    public User getUserByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");
        User user = null;
        String sql = "SELECT * FROM users WHERE username = LOWER(TRIM(?));";
        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
            if (rowSet.next()) {
                user = mapRowToUser(rowSet);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return user;
    }

    @Override
    public User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return getUserByUsername(username);
    }

    @Override
    public User createUser(RegisterUserDto user) {
        User newUser = null;
        String insertUserSql = "INSERT INTO users (username, password_hash, role) values (LOWER(TRIM(?)), ?, ?) RETURNING user_id";
        String password_hash = new BCryptPasswordEncoder().encode(user.getPassword());
        String ssRole = user.getRole().toUpperCase().startsWith("ROLE_") ? user.getRole().toUpperCase() : "ROLE_" + user.getRole().toUpperCase();
        try {
            int newUserId = jdbcTemplate.queryForObject(insertUserSql, int.class, user.getUsername(), password_hash, ssRole);
            newUser = getUserById(newUserId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newUser;
    }

    @Override
    public User updateUser(User user) {
        String sql = "update users set first_name = ?, last_name = ?, bio = ? where user_id = ?";
        try {
            jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(), user.getBio(), user.getId());
            deleteUserGenres(user.getId());
            List<Genre> genres = user.getGenres();
            if (genres != null) {
                for (Genre genre : genres) {
                    addUserGenre(genre.getGenreId(), user.getId());
                }
            }
            return getUserById(user.getId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Could not connect.", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public void addUserGenre(int genreId, int userId) {
        String sql = "insert into users_genres (genre_id, user_id) values (?, ?)";
        try {
            jdbcTemplate.update(sql, genreId, userId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Could not connect.", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public void removeUserGenre(int genreId, int userId) {
        String sql = "delete from users_genres where genre_id = ? and user_id = ?";
        try {
            jdbcTemplate.update(sql, genreId, userId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Could not connect.", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    public void deleteUserGenres(int userId) {
        String sql = "delete from users_genres where user_id = ?";
        try {
            jdbcTemplate.update(sql, userId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Could not connect.", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public void addFavoriteMovie(int userId, int movieId) {
        String sql = "insert into users_movies (user_id, movie_id) values (?, ?)";
        try {
            jdbcTemplate.update(sql, userId, movieId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Could not connect.", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public void removeFavoriteMovie(int userId, int movieId) {
        String sql = "delete from users_movies where user_id = ? and movie_id = ?";
        try {
            jdbcTemplate.update(sql, userId, movieId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Could not connect.", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));

        String firstName = rs.getString("first_name");
        if (firstName != null) {
            user.setFirstName(firstName);
        }

        String lastName = rs.getString("last_name");
        if (lastName != null) {
            user.setLastName(lastName);
        }

        String bio = rs.getString("bio");
        if (bio != null) {
            user.setBio(bio);
        }

        user.setAuthorities(Objects.requireNonNull(rs.getString("role")));
        user.setActivated(true);

        user.setGenres(genreDao.getGenresByUserId(user.getId()));

        return user;
    }
}
