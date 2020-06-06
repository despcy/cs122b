package com.cs122b.project.Fabflix.Service;

import com.cs122b.project.Fabflix.Response.*;
import com.cs122b.project.Fabflix.model.*;
import com.cs122b.project.Fabflix.session.CartSession;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Scope("singleton")
public class DBService {


    @Resource(name="readbean")
    private DataSource dataSource;

    @Resource(name="writebean")
    private DataSource writedataSource;

    @Autowired
    private DBQueryExec dbQueryExec;

    public ResultSet executeQuery(PreparedStatement stmt) throws Exception{
        return dbQueryExec.executeQuery(stmt);

    }

    public Movie getMovieByID(String movieID) throws Exception{



        String query="select * from movies where movies.id = ?";
        Connection conn=dataSource.getConnection();
        PreparedStatement Stmt=conn.prepareStatement(query);
        Stmt.setString(1,movieID);

        ArrayList<Movie> result=moviesFetch2(Stmt,false);
        if(result.size()>0){
            return result.get(0);
        }

        
        dbQueryExec.closeConn(conn);;
        return new Movie();
    }

    private Movie resultToMovie(ResultSet result) throws Exception{
        Movie mov=new Movie();
        mov.setId(result.getString("id"));
        mov.setTitle(result.getString("title"));
        mov.setDirector(result.getString("director"));
        mov.setYear(result.getInt("year"));
        return mov;
    }

    /*
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
     */


    public Star getStarById(String starId) throws Exception{
        String query = "select * from movies where movies.id in (select movieId from stars_in_movies where starId = ?) order by movies.year desc, movies.title asc";
        Connection conn=dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1,starId);
        List<Movie> movs=new ArrayList<>();
        ResultSet result = stmt.executeQuery();
        while(result.next()){
            movs.add(resultToMovie(result));
        }
        //ResultSet star=query("select * from stars where id = \""+starId+"\";");
        String query2="select * from stars where id = ?;";
        PreparedStatement stmt2 = conn.prepareStatement(query2);
        stmt2.setString(1,starId);
        ResultSet star = stmt2.executeQuery();
        star.next();
        Star s=resultToStar(star);
        s.setMovies(movs);
        dbQueryExec.closeConn(conn);;
        return s;
    }

    private Star resultToStar(ResultSet result) throws Exception{
        Star star=new Star();
        star.setId(result.getString("id"));
        star.setName(result.getString("name"));
        star.setBirthYear(result.getInt("birthYear"));
        return star;
    }

    public SearchResponse getSearchResult2(String title, String year, String director, String starName, int page, int pagesize,
                                          String sort, String order,Long[] tmp) throws Exception {

        Long TJ=0l;
        ArrayList<Movie> m_list = new ArrayList<>();
        String orderByCondition = "";
        String searchCondition = "where 1=1";
        String limitCondition = "";
        String starCondition = "";
        ArrayList<String> qparam=new ArrayList<>();


        StringBuilder sb = new StringBuilder();
        sb.append("+");
        for (int i = 0; i < title.length(); i++) {
            if (title.charAt(i) != ' ') {
                sb.append(title.charAt(i));
            }else {
                sb.append("*");
                sb.append(" ");
                sb.append("+");
            }
        }
        sb.append("*");
        String newTitle = sb.toString();

        if (title != null && !title.isEmpty())
        {
            searchCondition = searchCondition +" AND MATCH (movies.title) AGAINST (? IN BOOLEAN MODE)";
            qparam.add(newTitle);
        }

        if (year != null && !year.isEmpty())
        {
            searchCondition = searchCondition + " AND movies.year = ?";
            qparam.add(year);
        }

        if (director != null && !director.isEmpty())
        {
            searchCondition = searchCondition + " AND movies.director like ?";
            qparam.add("%"+director+"%");
        }
        if (starName != null && !starName.isEmpty())
        {
            starCondition = " and id in (select distinct movieId from stars_in_movies inner join stars on stars_in_movies.starId = stars.id where name like ?)";
            qparam.add("%"+starName+"%");
        }

        limitCondition = limitCondition + " LIMIT ?";

        int offset = 0;
        if (page == 1) offset = 0;
        else {
            offset = (page - 1) * pagesize;

        }
        limitCondition = limitCondition + " OFFSET ?";
        if (sort != null && !sort.isEmpty())
        {
            String ord1="asc";
            String ord2="desc";
            if(order.equals("asc_asc")){
                ord1="asc";
                ord2="asc";
            }else if(order.equals("asc_desc")){
                ord1="asc";
                ord2="desc";
            }else if(order.equals("desc_asc")){
                ord1="desc";
                ord2="asc";
            }
            else if(order.equals("desc_desc")){
                ord1="desc";
                ord2="desc";
            }

            if (sort.equals("title_rating"))
            {
                orderByCondition = " ORDER BY movies.title "+ord1+" , (select rating from ratings where ratings.movieId=movies.id) "+ ord2;
            }
            else if (sort.equals("rating_title"))
            {
                orderByCondition = " ORDER BY (select rating from ratings where ratings.movieId=movies.id) "+ord1+" , movies.title "+ ord2;
            }
        }


        //pagenation
        String querystr="select count(*) from movies "+searchCondition;

        if(starCondition!=""){
            querystr+=starCondition;
        }



        long start = System.nanoTime();
        Connection conn=dbQueryExec.getConnection(dataSource);
        PreparedStatement stmt=dbQueryExec.prepareStatement(conn,querystr);
        System.out.println(querystr);
        for(int i=0;i<qparam.size();i++){
            stmt.setString(i+1,qparam.get(i));
        }


        ResultSet q1=executeQuery(stmt);
        long executionTime = System.nanoTime() - start;
        TJ+=executionTime;
        long items = 0;
        while (q1.next()) {
            items = q1.getLong(1);}

        querystr="select * from movies "+searchCondition;

        if(starCondition!=""){
            querystr+=starCondition;
        }
        querystr +=orderByCondition;
        querystr+=limitCondition;

        start = System.nanoTime();
        stmt=dbQueryExec.prepareStatement(conn,querystr);
        executionTime = System.nanoTime() - start;
        TJ+=executionTime;
        for(int i=0;i<qparam.size();i++){
            stmt.setString(i+1,qparam.get(i));
        }
        stmt.setInt(qparam.size()+1,pagesize);
        stmt.setInt(qparam.size()+2,offset);

        start = System.nanoTime();
        m_list = moviesFetch2(stmt,true);
        executionTime = System.nanoTime() - start;
        TJ+=executionTime;
        Data data = new Data();
        data.setMovies(m_list);
        data.setCurPage(page);
        data.setPagesize(pagesize);
        data.setTotalItem(items);
        SearchResponse sr = new SearchResponse();
        sr.setMessage(0);
        sr.setData(data);
        start = System.nanoTime();
        dbQueryExec.closeConn(conn);
        executionTime = System.nanoTime() - start;
        TJ+=executionTime;
        tmp[0] = TJ;
        return sr;
    }






    public SearchResponse getSearchResult(String title, String year, String director, String starName, int page, int pagesize,
                                          String sort, String order) throws Exception {

        System.out.println(title);
        ArrayList<Movie> m_list = new ArrayList<>();
        String orderByCondition = "";
        String searchCondition = "where 1=1";
        String limitCondition = "";
        String starCondition = "";
        ArrayList<String> qparam=new ArrayList<>();
        if (title != null && !title.isEmpty())
        {
            searchCondition = searchCondition +" AND movies.title LIKE ?";
            qparam.add("%"+title+"%");
        }

        if (year != null && !year.isEmpty())
        {
            searchCondition = searchCondition + " AND movies.year = ?";
            qparam.add(year);
        }

        if (director != null && !director.isEmpty())
        {
            searchCondition = searchCondition + " AND movies.director like ?";
            qparam.add("%"+director+"%");
        }
        if (starName != null && !starName.isEmpty())
        {
            starCondition = " and id in (select distinct movieId from stars_in_movies inner join stars on stars_in_movies.starId = stars.id where name like ?)";
            qparam.add("%"+starName+"%");
        }

        limitCondition = limitCondition + " LIMIT ?";

        int offset = 0;
        if (page == 1) offset = 0;
        else {
            offset = (page - 1) * pagesize;

        }
        limitCondition = limitCondition + " OFFSET ?";
        if (sort != null && !sort.isEmpty())
        {
            String ord1="asc";
            String ord2="desc";
            if(order.equals("asc_asc")){
                ord1="asc";
                ord2="asc";
            }else if(order.equals("asc_desc")){
                ord1="asc";
                ord2="desc";
            }else if(order.equals("desc_asc")){
                ord1="desc";
                ord2="asc";
            }
            else if(order.equals("desc_desc")){
                ord1="desc";
                ord2="desc";
            }

            if (sort.equals("title_rating"))
            {
                orderByCondition = " ORDER BY movies.title "+ord1+" , (select rating from ratings where ratings.movieId=movies.id) "+ ord2;
            }
            else if (sort.equals("rating_title"))
            {
                orderByCondition = " ORDER BY (select rating from ratings where ratings.movieId=movies.id) "+ord1+" , movies.title "+ ord2;
            }
        }


        //pagenation
        String querystr="select count(*) from movies "+searchCondition;

        if(starCondition!=""){
            querystr+=starCondition;
        }

        Connection conn=dataSource.getConnection();
        PreparedStatement stmt=conn.prepareStatement(querystr);
        for(int i=0;i<qparam.size();i++){
            stmt.setString(i+1,qparam.get(i));
        }

        ResultSet q1=stmt.executeQuery();
        long items = 0;
        while (q1.next()) {
            items = q1.getLong(1);}

        querystr="select * from movies "+searchCondition;

        if(starCondition!=""){
            querystr+=starCondition;
        }
        querystr +=orderByCondition;
        querystr+=limitCondition;


        stmt=conn.prepareStatement(querystr);
        for(int i=0;i<qparam.size();i++){
            stmt.setString(i+1,qparam.get(i));
        }
        stmt.setInt(qparam.size()+1,pagesize);
        stmt.setInt(qparam.size()+2,offset);

        m_list = moviesFetch2(stmt,true);
        Data data = new Data();
        data.setMovies(m_list);
        data.setCurPage(page);
        data.setPagesize(pagesize);
        data.setTotalItem(items);
        SearchResponse sr = new SearchResponse();
        sr.setMessage(0);
        sr.setData(data);
        dbQueryExec.closeConn(conn);;
        return sr;
    }

    private ArrayList<Movie> moviesFetch2(PreparedStatement stat,Boolean limit) throws Exception {
        //select movieID from movie where



        ArrayList<Movie> movies =new ArrayList<>();

        ResultSet mtmp=executeQuery(stat);
        Connection conn=dbQueryExec.getConnection(dataSource);
        while(mtmp.next()) {
            Movie mov = resultToMovie(mtmp);
            String q;
            if(limit){
                q="select * from stars where stars.id in (select starId from stars_in_movies where movieId= ? ) order by (select count(*) from stars_in_movies where stars_in_movies.starId = stars.id) desc , stars.name asc limit 3";
            }else{
                q="select * from stars where stars.id in (select starId from stars_in_movies where movieId= ? ) order by (select count(*) from stars_in_movies where stars_in_movies.starId = stars.id) desc , stars.name asc";
            }

            PreparedStatement smt=dbQueryExec.prepareStatement(conn,q);
            smt.setString(1,mov.getId());
            ResultSet tmp = executeQuery(smt);
            ArrayList<Star> stars = new ArrayList<>();
            while (tmp.next()) {
                stars.add(resultToStar(tmp));
            }
            mov.setStars(stars);
            if(limit) {
                smt=dbQueryExec.prepareStatement(conn,"select * from genres where genres.id in (select genreId from genres_in_movies where movieId= ? ) order by name asc limit 3;");

            }else{
                smt=dbQueryExec.prepareStatement(conn,"select * from genres where genres.id in (select genreId from genres_in_movies where movieId= ? ) order by name asc;");

            }
            smt.setString(1,mov.getId());
            tmp=executeQuery(smt);
            ArrayList<Genre> genres = new ArrayList<>();
            while (tmp.next()) {
                Genre g = new Genre();
                g.setId(tmp.getInt("id"));
                g.setName(tmp.getString("name"));
                genres.add(g);
            }
            mov.setGenres(genres);

            smt=dbQueryExec.prepareStatement(conn,"select rating from ratings where movieId = ?;");
            smt.setString(1,mov.getId());
            tmp=executeQuery(smt);
            if(tmp.next()) {
                mov.setRating(tmp.getFloat("rating"));
            }else{
                mov.setRating(0.0f);
            }
            movies.add(mov);

        }
        dbQueryExec.closeConn(conn);;
        return movies;
    }



    public SearchResponse getGenreSearchResult(String genre, int page, int pagesize, String sort, String order) throws Exception {
        String orderByCondition = " ORDER BY movies.title ASC";
        String limitCondition = "";
        limitCondition = limitCondition + " limit ?";

        int offset = 0;
        if (page == 1) offset = 0;
        else
            offset = (page - 1)*pagesize;
        limitCondition = limitCondition + " OFFSET ?";
        if (sort != null && !sort.isEmpty())
        {
            String ord1="asc";
            String ord2="desc";
            if(order.equals("asc_asc")){
                ord1="asc";
                ord2="asc";
            }else if(order.equals("asc_desc")){
                ord1="asc";
                ord2="desc";
            }else if(order.equals("desc_asc")){
                ord1="desc";
                ord2="asc";
            }
            else if(order.equals("desc_desc")){
                ord1="desc";
                ord2="desc";
            }

            if (sort.equals("title_rating"))
            {
                orderByCondition = " ORDER BY movies.title "+ord1+" , (select rating from ratings where ratings.movieId=movies.id) "+ ord2;
            }
            else if (sort.equals("rating_title"))
            {
                orderByCondition = " ORDER BY (select rating from ratings where ratings.movieId=movies.id) "+ord1+" , movies.title "+ ord2;
            }
        }

        String countSQL = "select count(*) from movies where movies.id in (select movieId from genres_in_movies where genres_in_movies.genreId in (select id from genres where genres.name = ? ))";
        Connection conn=dataSource.getConnection();
        PreparedStatement stmt=conn.prepareStatement(countSQL);
        stmt.setString(1,genre);
        ResultSet q1=stmt.executeQuery();
        long items = 0;
        while (q1.next()) {
        items = q1.getLong(1);
        System.out.println(items);}

        String sql = "select * from movies where movies.id in (select movieId from genres_in_movies where genres_in_movies.genreId in (select id from genres where genres.name = ? ))"
                + orderByCondition
                + limitCondition;
        stmt=conn.prepareStatement(sql);
        stmt.setString(1,genre);
        stmt.setInt(2, pagesize);
        stmt.setInt(3, offset);
        ArrayList<Movie> m_list = moviesFetch2(stmt,true);

        Data data = new Data();
        data.setMovies(m_list);
        data.setCurPage(page);
        data.setPagesize(pagesize);
        data.setTotalItem(items);
        SearchResponse sr = new SearchResponse();
        sr.setMessage(0);
        sr.setData(data);
        dbQueryExec.closeConn(conn);;
        return sr;
    }

    public SearchResponse getAlphaSearchResult(String alphabet, int page, int pagesize, String sort, String order) throws Exception {

        String orderByCondition = " ORDER BY movies.title ASC";
        String limitCondition = "";
        limitCondition = limitCondition + " limit ?";

        int offset = 0;
        if (page == 1) offset = 0;
        else
            offset = (page - 1)*pagesize;
        limitCondition = limitCondition + " OFFSET ? ;";
        if (sort != null && !sort.isEmpty())
        {
            String ord1="asc";
            String ord2="desc";
            if(order.equals("asc_asc")){
                ord1="asc";
                ord2="asc";
            }else if(order.equals("asc_desc")){
                ord1="asc";
                ord2="desc";
            }else if(order.equals("desc_asc")){
                ord1="desc";
                ord2="asc";
            }
            else if(order.equals("desc_desc")){
                ord1="desc";
                ord2="desc";
            }

            if (sort.equals("title_rating"))
            {
                orderByCondition = " ORDER BY movies.title "+ord1+" , (select rating from ratings where ratings.movieId=movies.id) "+ ord2;
            }
            else if (sort.equals("rating_title"))
            {
                orderByCondition = " ORDER BY (select rating from ratings where ratings.movieId=movies.id) "+ord1+" , movies.title "+ ord2;
            }
        }

        if(alphabet.charAt(0)=='*'){
          alphabet="^[^a-z0-9]";   
        }else{
            alphabet="^"+alphabet;
           // System.out.println(alphabet);
        }
        String countSQL = "select count(*) from movies where movies.title REGEXP ?";
        Connection conn=dataSource.getConnection();
        PreparedStatement stmt=conn.prepareStatement(countSQL);
        stmt.setString(1,alphabet);

        ResultSet q1=stmt.executeQuery();

        long items = 0;
        while (q1.next()) {
            items = q1.getLong(1);
            System.out.println("aaaaaaaaa"+items);}

        String sql = "select * from movies where movies.title REGEXP ?"
                + orderByCondition
                + limitCondition;

        stmt=conn.prepareStatement(sql);
        stmt.setString(1,alphabet);
        stmt.setInt(2,pagesize);
        stmt.setInt(3,offset);
        ArrayList<Movie> m_list = moviesFetch2(stmt,true);

        Data data = new Data();
        data.setMovies(m_list);
        data.setCurPage(page);
        data.setPagesize(pagesize);
        data.setTotalItem(items);
        SearchResponse sr = new SearchResponse();
        sr.setMessage(0);
        sr.setData(data);
        dbQueryExec.closeConn(conn);;
        return sr;
    }

    //look for all genres sort alphabetical
    public ListGenResponse genlist() throws Exception {
        //ResultSet q=query("select * from genres order by ascii(lower(genres.name));");
        Connection conn=dataSource.getConnection();
        PreparedStatement Stmt = conn.prepareStatement("select * from genres order by ascii(lower(genres.name));");
        ResultSet q = Stmt.executeQuery();
        ArrayList<String> genlist =new ArrayList<>();
        while(q.next()){
            genlist.add(q.getString(2));

        }
        ListGenResponse res = new ListGenResponse();
        res.setMessage(0);
        res.setData(genlist);
        dbQueryExec.closeConn(conn);;
        return res;
    }

    //check credit card and add to sales
    public CheckoutResponse checkout(String firstname, String lastname, String number, String expire,
                                     String userId, HttpSession session) throws Exception {
        CheckoutResponse cr = new CheckoutResponse(1);
        //String sql = "select * from creditcards where creditcards.id = \"" +number+"\" and  creditcards.firstname = \"" +firstname+"\" and creditcards.lastname = \"" +lastname+"\" and creditcards.expiration = \"" +expire+"\";";
        String sql = "select * from creditcards where creditcards.id = ? and  creditcards.firstname = ? and creditcards.lastname = ? and creditcards.expiration = ?;";

        Connection conn=dataSource.getConnection();
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1,number);
        stm.setString(2, firstname);
        stm.setString(3, lastname);
        stm.setString(4, expire);

        ResultSet q1 = stm.executeQuery();
        boolean flag=false;
        while (q1.next()) {
            cr.setMessage(0);
            flag=true;
        }
        if(!flag){
            //false info
            return cr;
        }
        CartSession cs = (CartSession) session.getAttribute("cart");
        ArrayList<CartItem> cartItems = cs.getCartItems();
        cr.setCartList(cartItems);
        String sql_add ="INSERT INTO sales (customerId, movieId, saleDate) VALUES ( ?, ?, ?)";
        Connection connw=writedataSource.getConnection();
        PreparedStatement stmt = connw.prepareStatement(sql_add,Statement.RETURN_GENERATED_KEYS);

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date);

        ArrayList<String> sid = new ArrayList<>();

        for (CartItem item: cartItems) {

            //String add = " VALUES ( \'"+userId+"\', \'"+item.getMovieId()+"\', \'"+strDate+"\');";
            stmt.setString(1, userId);
            stmt.setString(2, item.getMovieId());
            stmt.setString(3, strDate);
            //System.out.println(sql_add+add);


            int quantity = item.getQuantity();
            for (int i = 0; i < quantity; i++){
                stmt.executeUpdate();
                //select.executeUpdate(sql_add+add, select.RETURN_GENERATED_KEYS);
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
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
            dbQueryExec.closeConn(conn);;
            dbQueryExec.closeConn(connw);;
        }

        cs.removeAllItemsFromCart();
        return cr;
    }

    public BaseResponse findByAccount(String email, String pwd) throws Exception {
        BaseResponse response = new BaseResponse(-1);

        //String sql = "select * from customers where customers.email = \""+ email +"\" and customers.password = \"" +pwd +"\";";
        String sql = "select * from customers where customers.email = ?;";
        Connection conn=dataSource.getConnection();
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, email);
        //stm.setString(2, pwd);

        ResultSet q1 = stm.executeQuery();
        System.out.println(sql);
        boolean success = false;


        if (q1.next()) {
            // get the encrypted password from the database
            String encryptedPassword = q1.getString("password");

            // use the same encryptor to compare the user input password with encrypted password stored in DB
            success = new StrongPasswordEncryptor().checkPassword(pwd, encryptedPassword);
            if (success) {
                response.setMessage(0);
                Customer cus = new Customer(q1.getString(1),q1.getString(2),q1.getString(3),
                        q1.getString(5), q1.getString(6), q1.getString(7));
                response.setData(cus);
            }

        }else
            response.setMessage(-1);
        dbQueryExec.closeConn(conn);;
        return response;
    }

    public BaseResponse adminLogin(String email, String password) throws SQLException {


        BaseResponse response = new BaseResponse(-1);
        System.out.println(email);
        System.out.println(password);
        
        //String sql = "select * from customers where customers.email = \""+ email +"\" and customers.password = \"" +pwd +"\";";
        String sql = "select * from employees where employees.email = ?;";
        Connection conn=dataSource.getConnection();
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, email);
        //stm.setString(2, pwd);

        ResultSet q1 = stm.executeQuery();
        System.out.println(sql);
        boolean success = false;
        String adminName="Rick";
        if (q1.next()) {
            // get the encrypted password from the database
            String encryptedPassword = q1.getString("password");
            adminName=q1.getString("fullname");
            // use the same encryptor to compare the user input password with encrypted password stored in DB
            success = new StrongPasswordEncryptor().checkPassword(password, encryptedPassword);
            if (success) {
                response.setMessage(0);
            }
        }

        //TODO:delete this
       // response.setMessage(0);


        response.setData(adminName);
        dbQueryExec.closeConn(conn);;
        return response;
    }

    //TODO: use moviedb only
    public ArrayList<Table> listTables(){
        ArrayList<Table> result=new ArrayList<>();
        DatabaseMetaData metaData = null;

        try {
            Connection conn=dataSource.getConnection();
            metaData = conn.getMetaData();

       String[] types = {"TABLE"};
        //Retrieving the columns in the database
        ResultSet tables = metaData.getTables("moviedb", "moviedb", null, types);
        while (tables.next()) {
            Table tb=new Table();
             tb.setName(tables.getString("TABLE_NAME"));
           //  System.out.println(tb.getName());
             result.add(tb);
        }
            dbQueryExec.closeConn(conn);;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
    public ArrayList<Attr> listAttr(String tablename){
        ArrayList<Attr> result=new ArrayList<>();
        DatabaseMetaData metaData = null;
        try {
            Connection conn=dataSource.getConnection();
            metaData = conn.getMetaData();

            String[] types = {"TABLE"};
            //Retrieving the columns in the database
            ResultSet columns = metaData.getColumns("moviedb","moviedb", tablename, null);
            while (columns.next()) {
                Attr at=new Attr();

                String columnName = columns.getString("COLUMN_NAME");
                String datatype = columns.getString("DATA_TYPE");
                at.setName(columnName);
                at.setType(datatype);
                result.add(at);
            }

            dbQueryExec.closeConn(conn);;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public BaseResponse addMovie(String title, String year, String director, String starName, String genre) throws SQLException {
        BaseResponse response = new BaseResponse(-1);
        Connection conn=writedataSource.getConnection();
        CallableStatement cs = conn.prepareCall("{CALL add_movie(?,?,?,?,?,?,?,?,?,?)}");

        cs.setString(1, title);
        cs.setInt(2, Integer.parseInt(year));
        cs.setString(3, director);
        cs.setString(4, starName);
        cs.setString(5, genre);
        cs.registerOutParameter(6,Types.INTEGER);
        cs.registerOutParameter(7,Types.VARCHAR);
        cs.registerOutParameter(8,Types.VARCHAR);
        cs.registerOutParameter(9,Types.INTEGER);
        cs.registerOutParameter(10,Types.VARCHAR);
        cs.executeUpdate();

        System.out.println(title+"   "+year+"   "+director);

        int msg = cs.getInt(6);
        String output = cs.getString(7);
        String mv_id = cs.getString(8);
        String gn_id = cs.getString(9);
        String st_id = cs.getString(10);
        System.out.println(msg);
        System.out.println(output);
        output = output + " Movie id is "+ mv_id + " Genre id is "+ gn_id +" Star id is "+ st_id;
        if(msg == 0) {
            response.setMessage(0);
            response.setData(output);
        }

        dbQueryExec.closeConn(conn);;
        return response;
    }

    public BaseResponse addStar(String name, String birth) throws SQLException {
        BaseResponse response = new BaseResponse(-1);
        Connection conn=writedataSource.getConnection();
        CallableStatement cs = conn.prepareCall("{CALL add_star(?,?,?,?,?)}");

        cs.setString(1, name);
        if (birth.equals("")) cs.setInt(2, 0);
        else
            cs.setInt(2, Integer.parseInt(birth));
        cs.registerOutParameter(3,Types.INTEGER);
        cs.registerOutParameter(4,Types.VARCHAR);
        cs.registerOutParameter(5,Types.VARCHAR);
        cs.executeUpdate();

        int msg = cs.getInt(3);
        String st_id = cs.getString(4);
        String output = cs.getString(5);
        output = output+" Star id is "+st_id;
        if(msg == 0) {
            response.setMessage(0);
            response.setData(output);
        }
        dbQueryExec.closeConn(conn);;
        return response;
    }

    public BaseResponse movieSearch(String text) throws SQLException {
        BaseResponse response = new BaseResponse(-1);
        StringBuilder sb = new StringBuilder();
        sb.append("+");
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != ' ') {
                sb.append(text.charAt(i));
            }else {
                sb.append("*");
                sb.append(" ");
                sb.append("+");
            }
        }
        sb.append("*");
        String newTitle = sb.toString();

        String sql = "select id, title from movies WHERE MATCH (movies.title) AGAINST (? IN BOOLEAN MODE);";
        Connection conn=dataSource.getConnection();
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, newTitle);
        ResultSet q1 = stm.executeQuery();
        List<Movie> res = new ArrayList<>();
        int count = 0;
        while(q1.next()) {
            if (count >= 10) break;
            Movie m = new Movie();
            m.setId(q1.getString(1));
            m.setTitle(q1.getString(2));
            res.add(m);
            count++;
        }
        response.setMessage(0);
        response.setData(res);
        dbQueryExec.closeConn(conn);;
        return response;
    }
}
