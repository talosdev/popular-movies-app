package app.we.go.movies.remote;

import android.util.Log;

import java.io.IOException;

import app.we.go.movies.remote.json.VideoList;
import app.we.go.movies.remote.json.VideosJSONParser;
import retrofit2.Call;
import retrofit2.Response;

import static app.we.go.movies.constants.TMDB.API_KEY;

/**
 * Created by apapad on 2/03/16.
 */
public class VideosFetcher extends JSONFetcher {

    private final TMDBService service;
    private VideosJSONParser parser = new VideosJSONParser();
    private URLBuilder urlBuilder = new URLBuilder();

    public VideosFetcher(TMDBService service) {
        this.service = service;
    }

    public VideoList fetch(long id) throws IOException {
//        URL url = urlBuilder.buildMovieVideosUrl(id);
//        String jsonString = getJSON(url);
//        if (jsonString == null) return null;
//        return parser.parse(jsonString);
        Call<VideoList> call = service.getVideos(id, API_KEY);
        Log.d("ZZZ", call.request().url().toString());

        Response<VideoList> response = call.execute();


//        Log.d("ZZZ", response.errorBody().toString());
        Log.d("ZZZ", response.code() + " - " + response.isSuccess());
        return response.body();
    }
}
