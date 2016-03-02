package app.we.go.movies.remote.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by apapad on 13/11/15.
 */
public class VideosJSONParser {


    private Gson gson;

    public VideosJSONParser() {
        gson = new GsonBuilder().create();
    }


    public VideoList parse(String json) {
        VideoList list = gson.fromJson(json, VideoList.class);
        return list;
    }

}
