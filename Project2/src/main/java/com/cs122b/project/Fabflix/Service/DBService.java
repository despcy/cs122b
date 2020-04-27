package com.cs122b.project.Fabflix.Service;

import com.cs122b.project.Fabflix.Response.*;
import com.cs122b.project.Fabflix.model.*;

import com.cs122b.project.Fabflix.session.CartSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import java.util.Date;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
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
        String query="select * from movies where movies.id = '"+movieID+"'";
        ArrayList<Movie> result=moviesFetch2(query,false);
        if(result.size()>0){
            return result.get(0);
        }
        return new Movie();
    }

    private ResultSet query(String queryStr) throws Exception{
        // Create an execute an SQL statement to select all of table"Stars" records
        Statement select = connection.createStatement();
        String query = queryStr;
        System.out.println(query);
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
        ResultSet result=query("select * from movies where movies.id in (select movieId from stars_in_movies where starId = \""+starId+"\") order by movies.year desc, movies.title asc");
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

        System.out.println(title);
        ArrayList<Movie> m_list = new ArrayList<>();
        String orderByCondition = " ORDER BY movies.title ASC, movies.year ASC";
        String searchCondition = "where 1=1";
        String limitCondition = "";
        String starCondition = "";
        if (title != null && !title.isEmpty())
        {
            searchCondition = searchCondition +" AND movies.title LIKE \"%" + title + "%\"";
        }

        if (year != null && !year.isEmpty())
        {
            searchCondition = searchCondition + " AND movies.year = " + year ;
        }

        if (director != null && !director.isEmpty())
        {
            searchCondition = searchCondition + " AND movies.director like \"%" + director + "%\"";
        }
        if (starName != null && !starName.isEmpty())
        {
            starCondition = " and id in (select distinct movieId from stars_in_movies inner join stars on stars_in_movies.starId = stars.id where name like '%"+starName+"%')";
        }

        limitCondition = limitCondition + " LIMIT " + pagesize;

        int offset = 0;
        if (page == 1) offset = 0;
        else
            offset = (page - 1)*pagesize;
        limitCondition = limitCondition + " OFFSET " + offset + ";";

        if (sort != null && !sort.isEmpty())
        {
            if (sort.equals("title") && order.equals("asc"))
            {
                orderByCondition = " ORDER BY movies.title ASC, (select rating from ratings where ratings.movieId=movies.id) ASC";
            }
            else if (sort.equals("title") && order.equals("desc"))
            {
                orderByCondition = " ORDER BY movies.title DESC, (select rating from ratings where ratings.movieId=movies.id) desc";
            }
            else if (sort.equals("rating")&& order.equals("asc"))
            {
                orderByCondition = " ORDER BY (select rating from ratings where ratings.movieId=movies.id) ASC, movies.title asc";
            }
            else if (sort.equals("rating")&& order.equals("desc"))
            {
                orderByCondition = " ORDER BY (select rating from ratings where ratings.movieId=movies.id) DESC, movies.year desc";
            }
        }
        //pagenation
        String querystr="select count(*) from movies "+searchCondition;

        if(starCondition!=""){
            querystr+=starCondition;
        }
        ResultSet q1=query(querystr);
        long items = 0;
        while (q1.next()) {
            items = q1.getLong(1);}

        querystr="select * from movies "+searchCondition;

        if(starCondition!=""){
            querystr+=starCondition;
        }
        querystr +=orderByCondition;
        querystr+=limitCondition;

        m_list = moviesFetch2(querystr,true);
        Data data = new Data();
        data.setMovies(m_list);
        data.setCurPage(page);
        data.setPagesize(pagesize);
        data.setTotalItem(items);
        SearchResponse sr = new SearchResponse();
        sr.setMessage(0);
        sr.setData(data);
        return sr;

    }

    private ArrayList<Movie> moviesFetch2(String querystr,Boolean limit) throws Exception {
        //select movieID from movie where

        System.out.println(querystr);


        ArrayList<Movie> movies =new ArrayList<>();

        ResultSet mtmp=query(querystr);
        while(mtmp.next()) {
            Movie mov = resultToMovie(mtmp);
            String q;
            if(limit){
                q="select * from stars where stars.id in (select starId from stars_in_movies where movieId='"+mov.getId()+"') order by (select count(*) from stars_in_movies where stars_in_movies.starId = stars.id) desc , stars.name asc limit 3";
            }else{
                q="select * from stars where stars.id in (select starId from stars_in_movies where movieId='"+mov.getId()+"') order by (select count(*) from stars_in_movies where stars_in_movies.starId = stars.id) desc , stars.name asc";
            }
            ResultSet tmp = query(q);
            ArrayList<Star> stars = new ArrayList<>();
            while (tmp.next()) {
                stars.add(resultToStar(tmp));
            }
            mov.setStars(stars);
            if(limit) {
                tmp = query("select * from genres where genres.id in (select genreId from genres_in_movies where movieId=\"" + mov.getId() + "\") order by name asc limit 3;");
            }else{
                tmp = query("select * from genres where genres.id in (select genreId from genres_in_movies where movieId=\"" + mov.getId() + "\") order by name asc;");

            }
            ArrayList<Genre> genres = new ArrayList<>();
            while (tmp.next()) {
                Genre g = new Genre();
                g.setId(tmp.getInt("id"));
                g.setName(tmp.getString("name"));
                genres.add(g);
            }
            mov.setGenres(genres);
            tmp = query("select rating from ratings where movieId = \"" + mov.getId() + "\";");
            if(tmp.next()) {
                mov.setRating(tmp.getFloat("rating"));
            }else{
                mov.setRating(0.0f);
            }
            movies.add(mov);
        }
        return movies;
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
        String orderByCondition = " ORDER BY movies.title ASC";
        String limitCondition = "";
        limitCondition = limitCondition + " limit " + pagesize;

        int offset = 0;
        if (page == 1) offset = 0;
        else
            offset = (page - 1)*pagesize;
        limitCondition = limitCondition + " OFFSET " + offset + ";";
        if (sort != null && !sort.isEmpty())
        {
            if (sort.equals("title") && order.equals("asc"))
            {
                orderByCondition = " ORDER BY movies.title ASC, (select rating from ratings where ratings.movieId=movies.id) ASC";
            }
            else if (sort.equals("title") && order.equals("desc"))
            {
                orderByCondition = " ORDER BY movies.title DESC, (select rating from ratings where ratings.movieId=movies.id) desc";
            }
            else if (sort.equals("rating")&& order.equals("asc"))
            {
                orderByCondition = " ORDER BY (select rating from ratings where ratings.movieId=movies.id) ASC, movies.title asc";
            }
            else if (sort.equals("rating")&& order.equals("desc"))
            {
                orderByCondition = " ORDER BY (select rating from ratings where ratings.movieId=movies.id) DESC, movies.year desc";
            }
        }

        String countSQL = "select count(*) from movies where movies.id in (select movieId from genres_in_movies where genres_in_movies.genreId in (select id from genres where genres.name = \"" + genre + "\" ))";
        ResultSet q1=query(countSQL);
        long items = 0;
        while (q1.next()) {
        items = q1.getLong(1);
        System.out.println(items);}

        String sql = "select * from movies where movies.id in (select movieId from genres_in_movies where genres_in_movies.genreId in (select id from genres where genres.name = \"" + genre + "\" ))"
                + orderByCondition
                + limitCondition;

        ArrayList<Movie> m_list = moviesFetch2(sql,true);

        Data data = new Data();
        data.setMovies(m_list);
        data.setCurPage(page);
        data.setPagesize(pagesize);
        data.setTotalItem(items);
        SearchResponse sr = new SearchResponse();
        sr.setMessage(0);
        sr.setData(data);
        return sr;
    }

    public SearchResponse getAlphaSearchResult(String alphabet, int page, int pagesize, String sort, String order) throws Exception {

        String orderByCondition = " ORDER BY movies.title ASC";
        String limitCondition = "";
        limitCondition = limitCondition + " limit " + pagesize;

        int offset = 0;
        if (page == 1) offset = 0;
        else
            offset = (page - 1)*pagesize;
        limitCondition = limitCondition + " OFFSET " + offset + ";";
        if (sort != null && !sort.isEmpty())
        {
            if (sort.equals("title") && order.equals("asc"))
            {
                orderByCondition = " ORDER BY movies.title ASC, (select rating from ratings where ratings.movieId=movies.id) ASC";
            }
            else if (sort.equals("title") && order.equals("desc"))
            {
                orderByCondition = " ORDER BY movies.title DESC, (select rating from ratings where ratings.movieId=movies.id) desc";
            }
            else if (sort.equals("rating")&& order.equals("asc"))
            {
                orderByCondition = " ORDER BY (select rating from ratings where ratings.movieId=movies.id) ASC, movies.title asc";
            }
            else if (sort.equals("rating")&& order.equals("desc"))
            {
                orderByCondition = " ORDER BY (select rating from ratings where ratings.movieId=movies.id) DESC, movies.year desc";
            }
        }


        if(alphabet.charAt(0)=='*'){
          alphabet="^[^a-z0-9]";   
        }
        String countSQL = "select count(*) from movies where movies.title REGEXP '^' \"" + alphabet + "\"";
        
        ResultSet q1=query(countSQL);

        long items = 0;
        while (q1.next()) {
            items = q1.getLong(1);
            System.out.println("aaaaaaaaa"+items);}

        String sql = "select * from movies where movies.title REGEXP '^' \"" + alphabet + "\""
                + orderByCondition
                + limitCondition;

        ArrayList<Movie> m_list = moviesFetch2(sql,true);

        Data data = new Data();
        data.setMovies(m_list);
        data.setCurPage(page);
        data.setPagesize(pagesize);
        data.setTotalItem(items);
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

    //check credit card and add to sales
    public CheckoutResponse checkout(String firstname, String lastname, String number, String expire,
                                     String userId, HttpSession session) throws Exception {
        CheckoutResponse cr = new CheckoutResponse(1);
        String sql = "select * from creditcards where creditcards.id = \"" +number+"\" and  creditcards.firstname = \"" +firstname+"\" and creditcards.lastname = \"" +lastname+"\" and creditcards.expiration = \"" +expire+"\";";
        ResultSet q1=query(sql);
        while (q1.next()) {
            cr.setMessage(0);
        }
        CartSession cs = (CartSession) session.getAttribute("cart");
        ArrayList<CartItem> cartItems = cs.getCartItems();
        cr.setCartList(cartItems);
        String sql_add ="INSERT INTO sales (customerId, movieId, saleDate)";

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date);

        ArrayList<String> sid = new ArrayList<>();

        for (CartItem item: cartItems) {

            String add = " VALUES ( \'"+userId+"\', \'"+item.getMovieId()+"\', \'"+strDate+"\');";
            //System.out.println(sql_add+add);
            Statement select = connection.createStatement();

            int quantity = item.getQuantity();
            for (int i = 0; i < quantity; i++){
                select.executeUpdate(sql_add+add, select.RETURN_GENERATED_KEYS);
                try (ResultSet generatedKeys = select.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        sid.add(Long.toString(generatedKeys.getLong(1)));
                    }
                    else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }

            }
            item.setSid(sid);
            sid=new ArrayList<>();
        }

        cs.removeAllItemsFromCart();
        return cr;
    }

    public BaseResponse findByAccount(String email, String pwd) throws Exception {
        BaseResponse response = new BaseResponse(-1);

        String sql = "select * from customers where customers.email = \""+ email +"\" and customers.password = \"" +pwd +"\";";
        ResultSet q1=query(sql);
        System.out.println(sql);

        while (q1.next()) {
            response.setMessage(0);
            Customer cus = new Customer(q1.getString(1),q1.getString(2),q1.getString(3),
                     q1.getString(5), q1.getString(6), q1.getString(7));
            response.setData(cus);
        }
        return response;
    }
}
