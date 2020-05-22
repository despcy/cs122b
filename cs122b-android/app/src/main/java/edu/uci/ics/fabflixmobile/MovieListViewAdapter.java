package edu.uci.ics.fabflixmobile;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import edu.uci.ics.fabflixmobile.pojo.Genre;
import edu.uci.ics.fabflixmobile.pojo.Movie;
import edu.uci.ics.fabflixmobile.pojo.Star;

import java.util.ArrayList;
import java.util.List;

public class MovieListViewAdapter extends ArrayAdapter<Movie> {
    private ArrayList<Movie> movies;

    public MovieListViewAdapter(ArrayList<Movie> movies, Context context) {
        super(context, R.layout.row, movies);
        this.movies = movies;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.row, parent, false);


        Movie movie = movies.get(position);

//        RowBinding rowBinding= DataBindingUtil.getBinding(convertView);
//        if(rowBinding==null){
//            rowBinding=DataBindingUtil.inflate(inflater,R.layout.row,parent,false);
//        }
//        rowBinding.setMovie(movie);
//        rowBinding.setGenres(G2S(movie));
//        rowBinding.setStars(S2S(movie));
//        rowBinding.executePendingBindings();
//        return rowBinding.getRoot();


        TextView titleView = view.findViewById(R.id.title);
        TextView year = view.findViewById(R.id.year);
        TextView dir = view.findViewById(R.id.director);
        TextView gen = view.findViewById(R.id.genre);
        TextView sta = view.findViewById(R.id.star);

        titleView.setText(movie.getTitle());
        year.setText(movie.getYear());
        dir.setText(movie.getDirector());
        gen.setText(G2S(movie));
        sta.setText(S2S(movie));
//        TextView subtitle_g3 = view.findViewById(R.id.subtitle_g3);
//        TextView subtitle_s1 = view.findViewById(R.id.subtitle_s1);
//        TextView subtitle_s2 = view.findViewById(R.id.subtitle_s2);
//        TextView subtitle_s3 = view.findViewById(R.id.subtitle_s3);
//
//        List<Genre> gl = movie.getGenres();
//
//        titleView.setText(movie.getTitle());
//        subtitle_year.setText(movie.getYear() + "");// need to cast the year to a string to set the label
//        subtitle_dire.setText(movie.getDirector());




        return view;
    }

    public String G2S(Movie movie){
        List<Genre> tp=movie.getGenres();
        StringBuilder sb=new StringBuilder();
        for(Genre g:tp){
            sb.append(g.getName());
            sb.append(" | ");
        }
        return sb.toString();
    }

    public String S2S(Movie movie){
        List<Star> tp=movie.getStars();
        StringBuilder sb=new StringBuilder();
        for(Star g:tp){
            sb.append(g.getName());
            sb.append(" | ");
        }
        return sb.toString();
    }




}