package com.cs122b.project.Fabflix.Response;

import com.cs122b.project.Fabflix.model.Movie;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MovieResponse {
    private Integer message;
    private Movie movie;

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }
    @JsonProperty("data")
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
