package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MovieList {

    @JsonProperty("list_id")
    private int listId;
    @JsonProperty("list_name")
    private String listName;
    private String description;

    public MovieList(int listId) {
        this.listId = listId;
    }

    public MovieList() {}

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
