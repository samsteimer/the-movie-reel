package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class Movie {

    @JsonProperty("movie_id")
    private int movieId;
    @JsonProperty("api_movie_id")
    private int apiMovieId;
    @NotNull
    private String title;
    @JsonProperty("poster_path")
    private String posterPath;
    private String overview;
    @JsonProperty("release_date")
    private String releaseDate;
    @JsonProperty("genres")
    private List<Genre> genres;
    @JsonIgnore
    private boolean isDeleted;

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

    public Movie(int movieId) {
        this.movieId = movieId;
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

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
