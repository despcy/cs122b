package com.cs122b.project.Fabflix.Service;

import com.cs122b.project.Fabflix.Response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Map;

@Service
public class MovieService {

    BufferedWriter filewriter=null;
    String curfile="";
    @Autowired
    private DBService dbService;

    @Value("${performancePath}")
    private String logpath;

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
                                 String sort, String order, Map<String, String> headers) {
        //start timing
        Long TS=new Long(0l);
        Long TJ=new Long(0l);




        try {

            if(!headers.containsKey("logfile")){
                headers.put("logfile","other");

            }
        if(!curfile.equals(headers.get("logfile"))) {
            curfile=headers.get("logfile");
            String file=logpath+curfile+".log";
            filewriter = new BufferedWriter(new FileWriter(file));



        }




        }catch (Exception e){
            e.printStackTrace();
        }
        SearchResponse sr = new SearchResponse();

        try {
            long start = System.nanoTime();
            Long[] tmp=new Long[1];
            tmp[0]=TJ;
            sr=dbService.getSearchResult2(title, year, director, starName, page, pagesize, sort, order,tmp);
            TJ=tmp[0];
            long executionTime = System.nanoTime() - start;
            TS=executionTime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //stop timing
        String out="{\"EndTime\":"+String.valueOf(System.nanoTime())+","+"\"TS\":"+String.valueOf(TS) + ","+"\"TJ\":"+String.valueOf(TJ)+"}\n";
        System.out.println(out);
        try {
            filewriter.write(out);
            filewriter.flush();
        }catch (Exception e){
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
    public ArrayList<Table> getTableInfo(){
        ArrayList<Table>result=dbService.listTables();
        for(int i=0;i<result.size();i++){

            result.get(i).setAttr(dbService.listAttr(result.get(i).getName()));
        }
        return result;

    }

    public BaseResponse addMovie(String title, String year, String director, String starName, String genre) {
        BaseResponse response = new BaseResponse(-1);
        try {
            response = dbService.addMovie(title, year, director, starName, genre);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    public BaseResponse addStar(String name, String birth) {
        BaseResponse response = new BaseResponse(-1);
        try {
            response = dbService.addStar(name, birth);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    public BaseResponse fullSearch(String text) {
        BaseResponse response = new BaseResponse(-1);
        try {
            response = dbService.movieSearch(text);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}

