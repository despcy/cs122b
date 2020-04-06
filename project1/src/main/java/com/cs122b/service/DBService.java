package com.cs122b.service;

import com.cs122b.entity.Genre;
import com.cs122b.entity.Movie;
import com.cs122b.entity.Star;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope("singleton")
public class DBService {



    private Connection connection;
    public DBService(@Value("${database.dbname}") String dbName,@Value("${database.username}") String userName,@Value("${database.password}") String password){


        // Connect to the test database
        try {
            // Incorporate mySQL driver
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql:///" + dbName + "?autoReconnect=true&useSSL=false",
                    userName, password);

        }catch (Exception e){
            System.out.println("jdbc:mysql:///" + dbName + "?autoReconnect=true&useSSL=false  "+userName +" "+password);
            System.out.println(e.toString());

        }

        if (connection != null) {
            System.out.println("Connection established!!");
            System.out.println();
        }
    }



    public Movie getMovieByID(String movieID) throws Exception{
        ResultSet tmp=query("select * from movies where movies.id = \""+ movieID+"\";");
        tmp.next();
        Movie mov=resultToMovie(tmp);
        tmp=query("select starId from stars_in_movies where movieId = \""+mov.getId()+"\";");
        ArrayList<Star> stars=new ArrayList<>();
        while(tmp.next()){
            ResultSet s=query("select * from stars where id = \""+tmp.getString("starId")+"\";");
            s.next();
            stars.add(resultToStar(s));
        }
        mov.setStars(stars);
        tmp=query("select genreId from genres_in_movies where movieId = \""+mov.getId()+"\";");
        ArrayList<Genre> genres=new ArrayList<>();
        while(tmp.next()){
            ResultSet s=query("select * from genres where id = "+tmp.getInt("genreId")+";");
            s.next();
            Genre g=new Genre();
            g.setId(s.getInt("id"));
            g.setName(s.getString("name"));
            genres.add(g);
        }
        mov.setGenres(genres);
        tmp=query("select rating from ratings where movieId = \""+mov.getId()+"\";");
        tmp.next();
        mov.setRating(tmp.getFloat("rating"));

        return mov;
    }

    private ResultSet query(String queryStr) throws Exception{
        // Create an execute an SQL statement to select all of table"Stars" records
        Statement select = connection.createStatement();
        String query = queryStr;
        ResultSet result = select.executeQuery(query);

        // Get metatdata from stars; print # of attributes in table
        System.out.println("The results of the query");
        ResultSetMetaData metadata = result.getMetaData();
        System.out.println("There are " + metadata.getColumnCount() + " columns");

        return result;
    }

    private Movie resultToMovie(ResultSet result) throws Exception{
        Movie mov=new Movie();
        mov.setId(result.getString("id"));
        mov.setTitle(result.getString("title"));
        mov.setDirector(result.getString("director"));
        mov.setYear(result.getInt("year"));
        return mov;
    }

    public List<Movie> getTop20Movies()throws Exception{
            ResultSet q=query("select * from ratings order by rating desc limit 20;");
            ArrayList<Movie> movies =new ArrayList<>();
            while(q.next()){
                ResultSet tmp=query("select * from movies where movies.id = \""+ q.getString("movieId")+"\";");
                tmp.next();
                Movie mov=resultToMovie(tmp);
                tmp=query("select starId from stars_in_movies where movieId = \""+mov.getId()+"\" limit 3;");
                ArrayList<Star> stars=new ArrayList<>();
                while(tmp.next()){
                    ResultSet s=query("select * from stars where id = \""+tmp.getString("starId")+"\";");
                    s.next();
                    stars.add(resultToStar(s));
                }
                mov.setStars(stars);
                tmp=query("select genreId from genres_in_movies where movieId = \""+mov.getId()+"\" limit 3;");
                ArrayList<Genre> genres=new ArrayList<>();
                while(tmp.next()){
                    ResultSet s=query("select * from genres where id = "+tmp.getInt("genreId")+";");
                    s.next();
                    Genre g=new Genre();
                    g.setId(s.getInt("id"));
                    g.setName(s.getString("name"));
                    genres.add(g);
                }
                mov.setGenres(genres);
                tmp=query("select rating from ratings where movieId = \""+mov.getId()+"\" limit 3;");
                tmp.next();
                mov.setRating(tmp.getFloat("rating"));
                movies.add(mov);
            }
            return movies;
    }



    public Star getStarById(String starId) throws Exception{
        ResultSet result=query("select * from movies where movies.id in (select movieId from stars_in_movies where starId = \""+starId+"\");");
        List<Movie> movs=new ArrayList<>();
        while(result.next()){
            movs.add(resultToMovie(result));
        }
        ResultSet star=query("select * from stars where id = \""+starId+"\";");
        star.next();
        Star s=resultToStar(star);
        s.setMovies(movs);
        return s;
    }

//    private List<String> getStarIdInMovie(String movID) throws Exception{
//        ResultSet result=query("select starId from stars_in_movies where movieId = "+movID);
//        ArrayList<String> ids=new ArrayList<>();
//        while(result.next()){
//            ids.add(result.getString("starId"));
//        }
//        return ids;
//    }
//
//    private Star getStarById(String starID) throws Exception{
//            return resultToStar(query("select * from stars where stars.id = "+starID));
//    }
//
    private Star resultToStar(ResultSet result) throws Exception{
            Star star=new Star();
            star.setId(result.getString("id"));
            star.setName(result.getString("name"));
            star.setBirthYear(result.getInt("birthYear"));
            return star;
    }
//
//    private List<Integer> getGenreIdInMovie(String movID) throws Exception{
//
//    }
//
//    private Genre getGenreById(Integer genreID) throws Exception{
//
//    }



}
