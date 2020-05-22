package edu.uci.ics.fabflixmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uci.ics.fabflixmobile.pojo.Data;
import edu.uci.ics.fabflixmobile.pojo.Movie;
import edu.uci.ics.fabflixmobile.pojo.SearchResp;

import java.io.IOException;
import java.util.ArrayList;

public class ListViewActivity extends Activity {

    private String url;
    private String response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        //this should be retrieved from the database and the backend server
        ArrayList<Movie> movies = new ArrayList<>();
        Intent intent=getIntent();
        response = intent.getStringExtra("data");
        ObjectMapper mapper = new ObjectMapper();
        SearchResp resp = new SearchResp();
        try {
            resp = mapper.readValue(response, SearchResp.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Data data = resp.getData();
        movies = (ArrayList<Movie>) data.getMovies();

        MovieListViewAdapter adapter = new MovieListViewAdapter(movies, this);

        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

        ArrayList<Movie> finalMovies = movies;
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
}