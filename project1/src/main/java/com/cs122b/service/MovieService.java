package com.cs122b.service;

import com.cs122b.entity.Movie;
import com.cs122b.entity.Star;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
   // @Autowired
   // private DBService dbService;
    public List<Movie> getMovies() {
        //top 20 movies
        List<Movie> movies = new ArrayList<Movie>();

        Movie mov=new Movie();
        mov.setId(123);
        mov.setTitle("nimaba");
        movies.add(mov);

        return movies;
    }

    //using id to get information from db
    public Movie movieDetail(int id) {

        Movie theMovie = new Movie();
        return theMovie;
    }

    //using id to get information from db
    public Star starDetail(int id) {

        Star theStar = new Star();
        return theStar;
    }
}
