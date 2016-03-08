package app.we.go.movies.listener;


import java.util.ArrayList;

import app.we.go.movies.remote.json.Video;

/**
 * Callback that is called when the trailer list for a movie is received.
 * Created by apapad on 29/02/16.
 */
public interface MovieVideosListener {

    void onMovieVideosReceived(ArrayList<Video> videos);
}
