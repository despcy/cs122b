package com.cs122b.project.Fabflix.DAO;

import com.cs122b.project.Fabflix.Response.Data;
import com.cs122b.project.Fabflix.Response.ListGenResponse;
import com.cs122b.project.Fabflix.Response.SearchResponse;
import com.cs122b.project.Fabflix.model.Customer;
import com.cs122b.project.Fabflix.model.Genre;
import com.cs122b.project.Fabflix.model.Movie;
import com.cs122b.project.Fabflix.model.Star;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Scope("singleton")
public class DBService {
    private Connection connection;
    public DBService(@Value("${database.dbname}") String dbName,@Value("${database.username}") String userName,@Value("${database.password}") String password){

        // Connect to the test database
        try {
            // Incorporate mySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql:///" + dbName + "?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT",
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

    private Star resultToStar(ResultSet result) throws Exception{
        Star star=new Star();
        star.setId(result.getString("id"));
        star.setName(result.getString("name"));
        star.setBirthYear(result.getInt("birthYear"));
        return star;
    }

    //under developing...
    public Customer login(Customer customer) {

        return null;
    }

    public SearchResponse getSearchResult(String title, String year, String director, String starName, int page, int pagesize,
                                          String sort, String order) throws Exception {
        ArrayList<Movie> m_list = new ArrayList<>();
        String orderByCondition = " ORDER BY movies.title ASC";
        String searchCondition = "where 1=1";
        String limitCondition = "";
        if (title != null && !title.isEmpty())
        {
            searchCondition = searchCondition +" AND movies.title LIKE \"" + title + "\"";
        }

        if (year != null && !year.isEmpty())
        {
            searchCondition = searchCondition + " AND movies.year LIKE \"" + year + "\"";
        }

        if (director != null && !director.isEmpty())
        {
            searchCondition = searchCondition + " AND movies.director = \"" + director + "\"";
        }
        if (starName != null && !starName.isEmpty())
        {
            searchCondition = searchCondition + " AND stars.name = \"" + starName + "\"";
        }

        limitCondition = limitCondition + " LIMIT " + pagesize;

        int offset = 0;
        if (page == 1) offset = 0;
        else
            offset = (page - 1)*pagesize;
        limitCondition = limitCondition + " OFFSET " + offset + ";";

        if (order != null && !order.isEmpty())
        {
            if (order.equals("titleasc"))
            {
                orderByCondition = " ORDER BY movies.title ASC";
            }
            else if (order.equals("titledsc"))
            {
                orderByCondition = " ORDER BY movies.title DESC";
            }
            else if (order.equals("yearasc"))
            {
                orderByCondition = " ORDER BY movies.year ASC";
            }
            else if (order.equals("yeardsc"))
            {
                orderByCondition = " ORDER BY movies.year DESC";
            }
        }
        m_list = moviesFetch(searchCondition, orderByCondition, limitCondition);
        Data data = new Data();
        data.setMovies(m_list);
        data.setCurPage(page);
        data.setPagesize(pagesize);
        data.setTotalItem(page);////////////////////////////////problem!!!!!!!/////////
        SearchResponse sr = new SearchResponse();
        sr.setMessage(0);
        sr.setData(data);
        return sr;

    }

    private ArrayList<Movie> moviesFetch(String searchCondition, String orderByCondition, String limitCondition) throws Exception {
        String sqlquery = "SELECT stars.id, stars.name, stars.birthYear, movies.id, movies.title, movies.year, movies.director, "
                + "genres.id, genres.name FROM movies "
                + "INNER JOIN stars_in_movies ON stars_in_movies.movieId = movies.id "
                + "INNER JOIN stars ON stars_in_movies.starId = stars.id "
                + "INNER JOIN genres_in_movies ON genres_in_movies.movieId = movies.id "
                + "INNER JOIN genres ON genres.id = genres_in_movies.genreId "
                + searchCondition
                + orderByCondition
                + limitCondition;
        ResultSet q = query(sqlquery);
        HashMap<String, Movie> movieMap = new HashMap<String, Movie>();

        while (q.next())
        {
            Star star = new Star(q.getString(1), q.getString(2), q.getInt(3));
            Genre genre = new Genre(q.getInt(8), q.getString(9));

            if (movieMap.containsKey(q.getString(4)))
            {
                Movie movie = movieMap.get(q.getString(4));
                boolean addStar = true;
                boolean addGenre = true;

                for (Star existingStar : movie.getStars())
                {
                    if (existingStar.getId().equals(star.getId()))
                    {
                        addStar = false;
                    }
                }

                if (addStar)
                {
                    movie.addStar(star);
                }

                for (Genre existingGenre : movie.getGenres())
                {
                    if (existingGenre.getId() == genre.getId())
                    {
                        addGenre = false;
                    }
                }

                if (addGenre)
                {
                    movie.addGenre(genre);
                }

                movieMap.put(movie.getId(), movie);
            }
            else
            {
                ArrayList<Genre> genres = new ArrayList<Genre>();
                genres.add(genre);

                ArrayList<Star> stars = new ArrayList<Star>();
                stars.add(star);

                Movie movie = new Movie(q.getString(4), q.getString(5), q.getInt(6),
                        q.getString(7), q.getInt(8),stars,genres);
                movieMap.put(movie.getId(), movie);
            }
        }
        ArrayList<Movie> movies = new ArrayList<Movie>(movieMap.values());
        return movies;

    }

    public SearchResponse getGenreSearchResult(String genre, int page, int pagesize, String sort, String order) throws Exception {
        ArrayList<Movie> m_list = new ArrayList<>();
        String orderByCondition = " ORDER BY movies.title ASC";
        String searchCondition = "where 1=1";
        String limitCondition = "";
        if (genre != null && !genre.isEmpty())
        {
            searchCondition = searchCondition +" AND genres.name LIKE \"" + genre + "\"";
        }

        limitCondition = limitCondition + " LIMIT " + pagesize;

        int offset = 0;
        if (page == 1) offset = 0;
        else
            offset = (page - 1)*pagesize;
        limitCondition = limitCondition + " OFFSET " + offset + ";";

        if (order != null && !order.isEmpty())
        {
            if (order.equals("titleasc"))
            {
                orderByCondition = " ORDER BY movies.title ASC;";
            }
            else if (order.equals("titledsc"))
            {
                orderByCondition = " ORDER BY movies.title DESC";
            }
            else if (order.equals("yearasc"))
            {
                orderByCondition = " ORDER BY movies.year ASC";
            }
            else if (order.equals("yeardsc"))
            {
                orderByCondition = " ORDER BY movies.year DESC";
            }
        }
        m_list = moviesFetch(searchCondition, orderByCondition, limitCondition);///////////////////////////////test order by error!!!!!!!!!!
        ///
        Data data = new Data();
        data.setMovies(m_list);
        data.setCurPage(page);
        data.setPagesize(pagesize);
        data.setTotalItem(page);////////////////////////////////problem!!!!!!!/////////
        SearchResponse sr = new SearchResponse();
        sr.setMessage(0);
        sr.setData(data);
        return sr;
    }

    public SearchResponse getAlphaSearchResult(String alphabet, int page, int pagesize, String sort, String order) throws Exception {
        ArrayList<Movie> m_list = new ArrayList<>();
        String orderByCondition = " ORDER BY movies.title ASC";
        String searchCondition = "where 1=1";
        String limitCondition = "";
        if (alphabet != null && !alphabet.isEmpty())
        {
            searchCondition = searchCondition +" AND movies.title REGEXP '^' \"" + alphabet.charAt(0) + "\"";
        }

        limitCondition = limitCondition + " LIMIT " + pagesize;

        int offset = 0;
        if (page == 1) offset = 0;
        else
            offset = (page - 1)*pagesize;
        limitCondition = limitCondition + " OFFSET " + offset + ";";

        if (order != null && !order.isEmpty())
        {
            if (order.equals("titleasc"))
            {
                orderByCondition = " ORDER BY movies.title ASC";
            }
            else if (order.equals("titledsc"))
            {
                orderByCondition = " ORDER BY movies.title DESC";
            }
            else if (order.equals("yearasc"))
            {
                orderByCondition = " ORDER BY movies.year ASC";
            }
            else if (order.equals("yeardsc"))
            {
                orderByCondition = " ORDER BY movies.year DESC";
            }
        }
        m_list = moviesFetch(searchCondition, orderByCondition, limitCondition);
        ///
        Data data = new Data();
        data.setMovies(m_list);
        data.setCurPage(page);
        data.setPagesize(pagesize);
        data.setTotalItem(page);////////////////////////////////problem!!!!!!!/////////
        SearchResponse sr = new SearchResponse();
        sr.setMessage(0);
        sr.setData(data);
        return sr;
    }

    //look for all genres sort alphabetical
    public ListGenResponse genlist() throws Exception {
        ResultSet q=query("select * from genres order by ascii(lower(genres.name));");
        ArrayList<String> genlist =new ArrayList<>();
        while(q.next()){
            genlist.add(q.getString(2));

        }
        ListGenResponse res = new ListGenResponse();
        res.setMessage(0);
        res.setData(genlist);
        return res;
    }
}
