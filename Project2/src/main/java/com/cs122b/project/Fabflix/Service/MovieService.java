package com.cs122b.project.Fabflix.Service;

import com.cs122b.project.Fabflix.Response.*;
import com.cs122b.project.Fabflix.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
    @Autowired
    private DBService dbService;

    public BaseResponse login(String email, String psw) throws Exception {
        BaseResponse response = new BaseResponse(-1);
        try {
            response = dbService.findByAccount(email, psw);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

//    public List<Movie> getTop20Movies() {
//        //top 20 movies
//
//        List<Movie> result=new ArrayList<>();
//        try {
//            result=dbService.getTop20Movies();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

    //using id to get information from db
    public MovieResponse movieDetail(String id) {

        MovieResponse result=new MovieResponse();
        result.setMessage(0);
        try {
            result.setMovie(dbService.getMovieByID(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //using id to get information from db
    public StarResponse starDetail(String id) {

        StarResponse theStar = new StarResponse();
        theStar.setMessage(0);
        try {
            theStar.setStar(dbService.getStarById(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return theStar;
    }

    public SearchResponse search(String title, String year, String director, String starName, int page, int pagesize,
                                 String sort, String order) {
        SearchResponse sr = new SearchResponse();
        try {
            sr=dbService.getSearchResult(title, year, director, starName, page, pagesize, sort, order);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sr;
    }

    public SearchResponse genreList(String genre, int page, int pagesize, String sort, String order) {
        SearchResponse sr = new SearchResponse();
        try {
            sr=dbService.getGenreSearchResult(genre, page, pagesize, sort, order);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sr;
    }

    public SearchResponse alphaList(String alphabet, int page, int pagesize, String sort, String order) {
        SearchResponse sr = new SearchResponse();
        try {
            sr=dbService.getAlphaSearchResult(alphabet, page, pagesize, sort, order);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sr;
    }

    public ListGenResponse genlist() {

        ListGenResponse sr = new ListGenResponse();
        try {
            sr=dbService.genlist();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sr;
    }

    public void addToCart(String movieId, String movieTitle) {
        //use session to add items to cart
        // Get a instance of current session on the request


    }

    public CartResponse getCart() {
        return null;
    }

    public CheckoutResponse checkoutService(String firstname, String lastname, String number,
                                            String expire, String userId, HttpSession session) {
        CheckoutResponse sr = new CheckoutResponse(1);
        try {
            sr=dbService.checkout(firstname, lastname, number, expire, userId, session);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sr;
    }

    public BaseResponse adminlogin(String email, String password) {
        BaseResponse response = new BaseResponse(-1);
        try {
            response = dbService.adminLogin(email, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}

