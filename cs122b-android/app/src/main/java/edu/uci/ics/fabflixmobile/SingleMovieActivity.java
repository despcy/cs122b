package edu.uci.ics.fabflixmobile;

//import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.uci.ics.fabflixmobile.databinding.ActivitySingleMovieBinding;
import edu.uci.ics.fabflixmobile.pojo.Data;
import edu.uci.ics.fabflixmobile.pojo.Genre;
import edu.uci.ics.fabflixmobile.pojo.Movie;
import edu.uci.ics.fabflixmobile.pojo.SearchResp;
import edu.uci.ics.fabflixmobile.pojo.SingMovieResp;
import edu.uci.ics.fabflixmobile.pojo.Star;

public class SingleMovieActivity extends Activity {

    String url=Constant.host+"/api/movie?movieId=";
    String movieID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_movie);
        ActivitySingleMovieBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_single_movie);
        Intent intent=getIntent();
        movieID = intent.getStringExtra("movieId");

//        Movie mov = new Movie();
//        mov.setTitle("hahah");
//        mov.setYear(1234);
//        binding.setSinglemov(mov);

        // Use the same network queue across our application
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        //request type is POST
        final StringRequest loginRequest = new StringRequest(Request.Method.GET, url+movieID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //TODO should parse the json response to redirect to appropriate functions.
                System.out.println(response);
                if (response.contains("\"message\":0")) {



                    ObjectMapper mapper = new ObjectMapper();
                    SingMovieResp resp = new SingMovieResp();
                    try {
                        resp = mapper.readValue(response, SingMovieResp.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Movie data = resp.getData();


                    binding.setSinglemov(data);
                    binding.setGenres(G2S(data));
                    binding.setStars(S2S(data));


                }else {
                    Toast.makeText(getApplicationContext(),"search fail!",Toast.LENGTH_LONG);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
                Log.d("login.error", error.toString());
                Toast.makeText(getApplicationContext(),"search error",Toast.LENGTH_SHORT);
            }
        });
        queue.add(loginRequest);

    }

    public String G2S(Movie movie){
        List<Genre> tp=movie.getGenres();
        StringBuilder sb=new StringBuilder();
        for(Genre g:tp){
            sb.append(g.getName());
            sb.append(" , ");
        }
        if(sb.length()>0){
            sb.delete(sb.length()-3,sb.length());
        }
        return sb.toString();
    }

    public String S2S(Movie movie){
        List<Star> tp=movie.getStars();
        StringBuilder sb=new StringBuilder();
        for(Star g:tp){
            sb.append(g.getName());
            sb.append(" , ");
        }
        if(sb.length()>0){
            sb.delete(sb.length()-3,sb.length());
        }
        return sb.toString();
    }


}
