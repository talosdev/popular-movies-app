package app.we.go.movies.model.remote;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by apapad on 2/03/16.
 */
public class VideoList {
    public VideoList() {

    }

    private long id;

    @SerializedName("results")
    private List<Video> videos;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
}
