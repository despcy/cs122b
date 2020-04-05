package com.cs122b.controller;

import java.util.List;

import com.cs122b.entity.Movie;
import com.cs122b.entity.Star;
import com.cs122b.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/movie")
public class MovieController {

	@Autowired
	private MovieService movieService;

	
	@RequestMapping("/list")
	public String listMovies(Model mm) {
		// the Movie list Page shows the top 20 rated movies, sorted by the rating
		List<Movie> theMovies =  movieService.getMovies();
		mm.addAttribute("movies", theMovies);
		return "movie-list";
	}

	@RequestMapping("/moviedetail")
	public String SingleMovie(@RequestParam("movieId") int id, Model mm) {

		Movie theMovie =  movieService.movieDetail(id);
		mm.addAttribute("movie", theMovie);
		return "movie-detail";
	}

	@RequestMapping("/stardetail")
	public String SingleStar(@RequestParam("starId") int id, Model mm) {
		Star theStar = movieService.starDetail(id);
		mm.addAttribute("star", theStar);
		return "star-detail";
	}



}
