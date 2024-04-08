package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class Movie {

    private Integer movieId;
    @JsonAlias("id")
    private int apiMovieId;
    private String title;
    private String posterPath;
    private String backdropPath;
    private int imdbId;
    private String homepagePath;
    private String overview;
    private LocalDate releaseDate;
    private int runtime;

    public Movie(int movieId, int apiMovieId, String title, String posterPath, String backdropPath, int imdbId, String homepagePath, String overview, LocalDate releaseDate, int runtime) {
        this.movieId = movieId;
        this.apiMovieId = apiMovieId;
        this.title = title;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.imdbId = imdbId;
        this.homepagePath = homepagePath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.runtime = runtime;
    }

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

    public String getHomepagePath() {
        return homepagePath;
    }

    public void setHomepagePath(String homepagePath) {
        this.homepagePath = homepagePath;
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
}
