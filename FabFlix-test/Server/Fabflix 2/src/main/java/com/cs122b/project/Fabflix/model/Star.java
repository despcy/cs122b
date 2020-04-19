package com.cs122b.project.Fabflix.model;

import java.util.List;

public class Star {

    private String id;

    private String name;

    private int birthYear;

    private List<Movie> movies;

    public Star() {
    }

    public Star(String id, String name, int birthYear, List<Movie> movies) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
        this.movies = movies;
    }

    public String getName() {
        return name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

