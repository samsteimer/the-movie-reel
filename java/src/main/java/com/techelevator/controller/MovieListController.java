package com.techelevator.controller;

import com.techelevator.dao.MovieListDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.MovieList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/list")
public class MovieListController {

    @Autowired
    private MovieListDao movieListDao;

    @GetMapping
    public List<MovieList> getMovieListsByName(@RequestParam String name) {
        try {
            List<MovieList> movieLists = movieListDao.getMovieListsByName(name);
            if (movieLists == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "List not found.");
            } else {
                return movieLists;
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not Available");
        }
    }

    @GetMapping("{id}")
    public MovieList getMovieList(@PathVariable int id) {
        try {
            MovieList movieList = movieListDao.getMovieListById(id);
            if (movieList == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "List not found.");
            } else {
                return movieList;
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not Available");
        }
    }

}
