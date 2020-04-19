package com.cs122b.project.Fabflix.controller;

import java.util.List;

import com.cs122b.project.Fabflix.DAO.MovieService;
import com.cs122b.project.Fabflix.model.Movie;
import com.cs122b.project.Fabflix.model.Star;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/movies")
    public List<Movie> listMovies() {
        // the Movie list Page shows the top 20 rated movies, sorted by the rating
        return movieService.getTop20Movies();
    }

    @GetMapping("/movies/{movieId}")
    public Movie SingleMovie(@PathVariable String id) {

        return movieService.movieDetail(id);
    }

    @GetMapping("/stars/{starId}")
    public Star SingleStar(@PathVariable String id) {
        return movieService.starDetail(id);
    }



}
