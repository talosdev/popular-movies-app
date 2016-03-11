package app.we.go.movies.remote;

import java.io.IOException;

import app.we.go.movies.remote.json.VideoList;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by apapad on 2/03/16.
 */
public class VideosFetcher extends JSONFetcher {

    private final TMDBService service;

    public VideosFetcher(TMDBService service) {
        this.service = service;
    }

    public VideoList fetch(long id) throws IOException {
        Call<VideoList> call = service.getVideos(id);

        Response<VideoList> response = call.execute();

        return response.body();
    }
}
