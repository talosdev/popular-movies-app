package com.talosdev.movies.ui.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.talosdev.movies.R;
import com.talosdev.movies.constants.TMDB;
import com.talosdev.movies.data.MoviePoster;
import com.talosdev.movies.data.SortByCriterion;
import com.talosdev.movies.remote.FetchPopularMoviesTask;
import com.talosdev.movies.remote.json.Movie;
import com.talosdev.movies.remote.json.MovieJSONParser;
import com.talosdev.movies.remote.json.MovieList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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






}
