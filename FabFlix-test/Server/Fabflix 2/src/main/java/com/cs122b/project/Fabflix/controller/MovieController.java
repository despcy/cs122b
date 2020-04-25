package com.cs122b.project.Fabflix.controller;

import java.util.List;

import com.cs122b.project.Fabflix.Service.CustomerService;
import com.cs122b.project.Fabflix.Service.MovieService;
import com.cs122b.project.Fabflix.Response.*;
import com.cs122b.project.Fabflix.model.Customer;
import com.cs122b.project.Fabflix.model.Movie;
import com.cs122b.project.Fabflix.session.CartSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


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

    @GetMapping("/star")
    public StarResponse SingleStar(@RequestParam("starId") String starId) {
        return movieService.starDetail(starId);
    }

    @PostMapping("/login")
    public BaseResponse CustomerLogin(@RequestParam("email")String email, @RequestParam("password") String password,
                                      HttpSession session) throws Exception {
        BaseResponse response = movieService.login(email,password);
        if (response.getMessage() == 0){
            session.setAttribute(session.getId(),response);
            session.setAttribute("cart",new CartSession());
        }
        return response;
    }
    @RequestMapping(value = "/user")
    public BaseResponse getUserName(HttpSession session){


        return  (BaseResponse) session.getAttribute(session.getId());
    }


    @RequestMapping(value = "/logout")
    public String logout(HttpSession session){
        session.removeAttribute(session.getId());
        return "{\"message\":-1,\"data\":\"Logout Success!\"}";
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

    @PostMapping("/cart/add")
    public void addToCart(@RequestParam("movieId") String movieId, @RequestParam("movieTitle") String movieTitle){
        movieService.addToCart(movieId,movieTitle);
    }

    @PostMapping("cart/update")
    public void updateCart(){

    }

    @GetMapping("cart/show")
    public CartResponse showCart(){
        return movieService.getCart();
    }

    @PostMapping("cart/checkout")
    public CheckoutResponse checkout(@RequestParam("first") String firstname, @RequestParam("last") String lastname,
                         @RequestParam("number") String number, @RequestParam("movieId") String expire){
        return movieService.checkoutService(firstname, lastname, number, expire);
    }











}
