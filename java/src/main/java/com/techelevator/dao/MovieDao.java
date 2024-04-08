package com.techelevator.dao;

import com.techelevator.model.Movie;

import java.util.List;

public interface MovieDao {

    Movie getMovieByMovieId(Integer id);

    Movie getMovieByTitle(String title);

    List<Movie> getMoviesByGenreId(int id);

    List<Movie> getMoviesByUserId(int userId);

    List<Movie> getMoviesByListId(int listId);

    List<Movie> getMoviesByListId(List<Integer> listIds);

    List<Movie> getMoviesByGenreId(List<Integer> genreIds);






}
