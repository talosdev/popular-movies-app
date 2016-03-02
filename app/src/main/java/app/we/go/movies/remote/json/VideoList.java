package app.we.go.movies.remote.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by apapad on 2/03/16.
 */
public class VideoList {
    public VideoList() {

    }

    public long id;

    @SerializedName("results")
    public List<Video> videos;
}
