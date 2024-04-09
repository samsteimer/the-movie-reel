package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class Movie {

    private Integer movieId;
    @JsonAlias("id")
    private int apiMovieId;
    @NotNull
    private String title;
    private String posterPath;
    private String backdropPath;
    private int imdbId;
    private String homepage;
    private String overview;
    private LocalDate releaseDate;
    private int runtime;
    private List<Genre> genres;

//    public Movie(int movieId, int apiMovieId, String title, String posterPath, String backdropPath, int imdbId,
//                 String homepage, String overview, LocalDate releaseDate, int runtime, List<Genre> genres) {
//        this.movieId = movieId;
//        this.apiMovieId = apiMovieId;
//        this.title = title;
//        this.posterPath = posterPath;
//        this.backdropPath = backdropPath;
//        this.imdbId = imdbId;
//        this.homepage = homepage;
//        this.overview = overview;
//        this.releaseDate = releaseDate;
//        this.runtime = runtime;
//        this.genres = genres;
//    }

    public Movie() {

    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public int getApiMovieId() {
        return apiMovieId;
    }

    public void setApiMovieId(int apiMovieId) {
        this.apiMovieId = apiMovieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public int getImdbId() {
        return imdbId;
    }

    public void setImdbId(int imdbId) {
        this.imdbId = imdbId;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
