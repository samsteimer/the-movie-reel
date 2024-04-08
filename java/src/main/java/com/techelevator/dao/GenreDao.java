package com.techelevator.dao;

import com.techelevator.model.Genre;

import java.util.List;

public interface GenreDao {

    Genre getGenreById(int id);

    List<Genre> getListOfGenres();
    Genre getGenreByName(String name);
}
