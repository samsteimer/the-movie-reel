package com.techelevator.model;

import java.util.List;

public class MovieListApiDto {
    private List<MovieApiDto> results;

    public List<MovieApiDto> getResults() {
        return results;
    }

    public void setResults(List<MovieApiDto> results) {
        this.results = results;
    }
}
