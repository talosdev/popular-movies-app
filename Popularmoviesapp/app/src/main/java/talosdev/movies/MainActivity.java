package talosdev.movies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import talosdev.movies.data.MoviePoster;
import talosdev.movies.remote.json.Movie;
import talosdev.movies.remote.json.MovieJSONParser;
import talosdev.movies.remote.json.MovieList;

public class MainActivity extends AppCompatActivity {

    MovieList movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieList = loadDummyData();

        setContentView(R.layout.activity_main);

        Log.d("MAIN", movieList.movies.size() + "");
    }

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
            String basePath = "http://image.tmdb.org/t/p/w154";
            urls.add(new MoviePoster(movie.id, basePath + poster));
        }
        return urls;
    }



}
