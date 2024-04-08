package com.techelevator.dao;

import com.techelevator.model.MovieList;

import java.util.List;

public interface MovieListDao {

    MovieList getMovieListById(int id);

    List<MovieList> getMovieListsById(int id);

    List<MovieList> getMovieListsById(List<Integer> ids);

    List<MovieList> getMovieListsByName(String name);

    MovieList createMovieList(String name, String description);

    MovieList updateMovieListById(int id, String name, String description);

}
