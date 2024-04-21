package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MovieApiDto {
    private String title;
    @JsonProperty("id")
    private int apiMovieId;
    @JsonProperty("poster_path")
    private String posterPath;
    private String overview;
    @JsonProperty("release_date")
    private String releaseDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getApiMovieId() {
        return apiMovieId;
    }

    public void setApiMovieId(int apiMovieId) {
        this.apiMovieId = apiMovieId;
    }

    public String getPosterPath() {
        return "https://image.tmdb.org/t/p/w200" + posterPath;
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
}
