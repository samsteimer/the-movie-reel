package com.techelevator.dao;

import com.techelevator.model.RegisterUserDto;
import com.techelevator.model.User;

import java.security.Principal;
import java.util.List;

public interface UserDao {

    List<User> getUsers();

    User getUserById(int id);

    User getUserByUsername(String username);

    User getUserByPrincipal(Principal principal);

    User createUser(RegisterUserDto user);

    User updateUser(User user);

    void addUserGenre(int userId, int genreId);

    void removeUserGenre(int userId, int genreId);

    void addFavoriteMovie(int userId, int movieId);

    void removeFavoriteMovie(int userId, int movieId);

}
