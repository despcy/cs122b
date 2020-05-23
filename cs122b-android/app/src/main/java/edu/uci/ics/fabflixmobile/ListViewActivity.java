package edu.uci.ics.fabflixmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uci.ics.fabflixmobile.pojo.Data;
import edu.uci.ics.fabflixmobile.pojo.Movie;
import edu.uci.ics.fabflixmobile.pojo.SearchResp;

import java.io.IOException;
import java.util.ArrayList;

public class ListViewActivity extends Activity {

    private String url;
    private String response;
    private Integer curPage=1;
    private Long totalItem=0l;
    ArrayList<Movie> finalMovies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        //this should be retrieved from the database and the backend server
        ArrayList<Movie> movies = new ArrayList<>();
        Intent intent=getIntent();
        response = intent.getStringExtra("data");
        url=intent.getStringExtra("url");
        ObjectMapper mapper = new ObjectMapper();
        SearchResp resp = new SearchResp();
        try {
            resp = mapper.readValue(response, SearchResp.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        totalItem=resp.getData().getTotalItem();
        Data data = resp.getData();
        movies = (ArrayList<Movie>) data.getMovies();

        MovieListViewAdapter adapter = new MovieListViewAdapter(movies, this);

        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

        finalMovies = movies;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = finalMovies.get(position);
                //String message = String.format("Clicked on position: %d, name: %s, %d", position, movie.getName(), movie.getYear());
                //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ListViewActivity.this,SingleMovieActivity.class);
                intent.putExtra("movieId", movie.getId());
                startActivity(intent);

            }
        });


    }


    public void nextPage(View v){

        if(curPage*20>totalItem){
            //no nextPage
            Toast.makeText(v.getContext(),"No Next page",Toast.LENGTH_SHORT);
        }else{
            curPage++;
            TextView tmp=((TextView)findViewById(R.id.pagenum));
            tmp.setText("Page "+String.valueOf(curPage));
            SearchByPage(String.valueOf(curPage),v);

        }
    }

    public void prevPage(View v){

        if(curPage==1){
            //no prevPage
            Toast.makeText(v.getContext(),"No previous page",Toast.LENGTH_SHORT);
        }else{
            curPage--;
            TextView tmp=((TextView)findViewById(R.id.pagenum));
            tmp.setText("Page "+String.valueOf(curPage));
            SearchByPage(String.valueOf(curPage),v);
        }

    }

    public void setData(ArrayList<Movie> movies){
        MovieListViewAdapter adapter = new MovieListViewAdapter(movies, this);

        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
        finalMovies=movies;
    }

    public void SearchByPage(String page, View v){

            // Use the same network queue across our application
            final RequestQueue queue = NetworkManager.sharedManager(this).queue;
            //request type is POST
            final StringRequest loginRequest = new StringRequest(Request.Method.GET, url + page, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //TODO should parse the json response to redirect to appropriate functions.
                    System.out.println(response);
                    if (response.contains("\"message\":0")) {

                        Log.d("login.success", response);///////////???

                        ObjectMapper mapper = new ObjectMapper();
                        SearchResp resp = new SearchResp();
                        try {
                            resp = mapper.readValue(response, SearchResp.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        totalItem=resp.getData().getTotalItem();
                        Data data = resp.getData();
                        ArrayList<Movie> movies = (ArrayList<Movie>) data.getMovies();

                        MovieListViewAdapter adapter = new MovieListViewAdapter(movies, v.getContext());

                        ListView listView = findViewById(R.id.list);
                        listView.setAdapter(adapter);

                        finalMovies = movies;


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
}