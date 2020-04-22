package com.cs122b.project.Fabflix.controller;

import java.util.List;

import com.cs122b.project.Fabflix.DAO.CustomerService;
import com.cs122b.project.Fabflix.DAO.MovieService;
import com.cs122b.project.Fabflix.Response.ListGenResponse;
import com.cs122b.project.Fabflix.Response.MovieResponse;
import com.cs122b.project.Fabflix.Response.SearchResponse;
import com.cs122b.project.Fabflix.Response.StarResponse;
import com.cs122b.project.Fabflix.model.Customer;
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

    private CustomerService customerService;

    @GetMapping("/movies")
    public List<Movie> ListMovies() {
        // the Movie list Page shows the top 20 rated movies, sorted by the rating
        return movieService.getTop20Movies();
    }

    @GetMapping("/movie")
    public MovieResponse SingleMovie(@RequestParam("movieId") String movieId) {

        return movieService.movieDetail(movieId);
    }

    @GetMapping("/stars")
    public StarResponse SingleStar(@RequestParam("starId") String starId) {
        return movieService.starDetail(starId);
    }

    @PostMapping("/login")
    public Customer CustomerLogin(@RequestBody Customer customer){
        return customerService.login(customer);
    }

    //Search movie: with substring matching: /api/search?title=t&year=year&director=d&star=s&page=1&pagesize=20&sort=title&order=asc
    @GetMapping("/search")
    public SearchResponse Search(@RequestParam("title") String title, @RequestParam("year") String year,
                                 @RequestParam("director") String director, @RequestParam("star") String starName,
                                 @RequestParam("page") int page, @RequestParam("pagesize") int pagesize,
                                 @RequestParam("sort") String sort, @RequestParam("order") String order) {
        return movieService.search(title, year, director, starName, page, pagesize, sort, order);
    }

    //search movies in a genre:
    @GetMapping("/list")
    public SearchResponse search_in_genre(@RequestParam("genre") String genre, @RequestParam("page") int page,
                                        @RequestParam("pagesize") int pagesize, @RequestParam("sort") String sort,
                                        @RequestParam("order") String order){
        return movieService.genreList(genre, page, pagesize, sort, order);
    }

    //search movies start with alphabet:
    @GetMapping("/listalpha")
    public SearchResponse search_in_alpha(@RequestParam("alphabet") String alphabet, @RequestParam("page") int page,
                                        @RequestParam("pagesize") int pagesize, @RequestParam("sort") String sort,
                                        @RequestParam("order") String order){
        return movieService.alphaList(alphabet, page, pagesize, sort, order);
    }

    //List all movie genre sort alphabetical: /api/genres
    @GetMapping("/genres")
    public ListGenResponse list_in_alpha(){
        return movieService.genlist();
    }






}
