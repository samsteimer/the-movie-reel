package com.techelevator.dao;

import com.techelevator.model.MovieList;

import java.util.List;

public interface MovieListDao {

    MovieList getMovieListById(int id);

    List<MovieList> getMovieListsByName(String name);

    MovieList createMovieList(MovieList movieList);

    MovieList updateMovieList(MovieList movieList);

    void addListMovie(int listId, int movieId);

    void removeListMovie(int listId, int movieId);

}
