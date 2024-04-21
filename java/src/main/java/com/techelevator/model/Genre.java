package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Genre {
    @JsonProperty("genre_id")
    private int genreId;
    @JsonProperty("genre_name")
    private String genreName;

    public Genre(int genreId) {
        this.genreId = genreId;
    }

    public Genre(int genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
    }

    public Genre() {

    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
