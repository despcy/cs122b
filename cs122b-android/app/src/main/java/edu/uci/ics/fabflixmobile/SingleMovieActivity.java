package edu.uci.ics.fabflixmobile;

//import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import edu.uci.ics.fabflixmobile.databinding.ActivitySingleMovieBinding;
import edu.uci.ics.fabflixmobile.pojo.Movie;

public class SingleMovieActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_movie);
        ActivitySingleMovieBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_single_movie);
        Intent intent=getIntent();
        String id = intent.getStringExtra("movieId");
        Movie mov = new Movie();
        mov.setTitle("hahah");
        mov.setYear(1234);
        binding.setSinglemov(mov);
    }
}
