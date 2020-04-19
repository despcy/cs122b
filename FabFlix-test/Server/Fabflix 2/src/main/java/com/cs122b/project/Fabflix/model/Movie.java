package com.cs122b.project.Fabflix.model;

import java.util.List;

public class Movie {

    private String id;

    private String title;

    private int year;

    private String director;

    private float rating;

    private List<Star> stars;

    private List<Genre> genres;

    public Movie() {
    }

    public Movie(String id, String title, int year, String director, float rating, List<Star> stars, List<Genre> genres) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.director = director;
        this.rating = rating;
        this.stars = stars;
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getDirector() {
        return director;
    }

    public float getRating() {
        return rating;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<Star> getStars() {
        return stars;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setStars(List<Star> stars) {
        this.stars = stars;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

