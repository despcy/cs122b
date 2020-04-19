package com.cs122b.project.Fabflix.DAO;

import com.cs122b.project.Fabflix.model.Movie;
import com.cs122b.project.Fabflix.model.Star;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
    @Autowired
    private DBService dbService;


    public List<Movie> getMovies() {
        //top 20 movies
        List<Movie> movies = new ArrayList<Movie>();


        return movies;
    }


    public List<Movie> getTop20Movies() {
        //top 20 movies

        List<Movie> result=new ArrayList<>();
        try {
            result=dbService.getTop20Movies();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    //using id to get information from db
    public Movie movieDetail(String id) {

        Movie result=new Movie();
        try {
            result=dbService.getMovieByID(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //using id to get information from db
    public Star starDetail(String id) {

        Star theStar = new Star();
        try {
            theStar=dbService.getStarById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return theStar;
    }
}

