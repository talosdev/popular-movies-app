package com.talosdev.movies.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.talosdev.movies.R;
import com.talosdev.movies.constants.TMDB;
import com.talosdev.movies.data.MoviePoster;
import com.talosdev.movies.remote.json.Movie;
import com.talosdev.movies.remote.json.MovieJSONParser;
import com.talosdev.movies.remote.json.MovieList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MovieList movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO access real data
        movieList = loadDummyData();

        setContentView(R.layout.activity_main);

        Log.d("MAIN", movieList.movies.size() + "");
    }

    /**
     * Temp method that populates reads data to populate the gridview from a file with
     * dummy data instead of calling the api.
     * @return
     */
    private MovieList loadDummyData() {
        MovieJSONParser parser = new MovieJSONParser();


        InputStream is = getResources().openRawResource(R.raw.movie_list);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String readLine = null;
        StringBuffer sb = new StringBuffer();

        try {
            while ((readLine = br.readLine()) != null) {

                sb.append(readLine);
            }

        } catch (IOException e) {
            e.printStackTrace(); //create exception output
        }

        String s = sb.toString();

        return parser.parseMovieList(s);
    }


//    "poster_sizes": [
//            "w92",
//            "w154",
//            "w185",
//            "w342",
//            "w500",
//            "w780",
//            "original"
//            ],


    public List<MoviePoster> getPosterURLs() {
        List<MoviePoster> urls = new ArrayList<>(movieList.movies.size());
        for (Movie movie:movieList.movies) {
            String poster = movie.posterPath;

            urls.add(new MoviePoster(movie.id, TMDB.buildPosterUrl(poster)));
        }
        return urls;
    }



}
