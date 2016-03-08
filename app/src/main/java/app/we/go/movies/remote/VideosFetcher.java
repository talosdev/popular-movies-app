package app.we.go.movies.remote;

import java.io.IOException;
import java.net.URL;

import app.we.go.movies.remote.json.VideoList;
import app.we.go.movies.remote.json.VideosJSONParser;

/**
 * Created by apapad on 2/03/16.
 */
public class VideosFetcher  extends JSONFetcher {
    private VideosJSONParser parser = new VideosJSONParser();
    private URLBuilder urlBuilder = new URLBuilder();

    public VideosFetcher() {

    }

    public VideoList fetch(long id) throws IOException {
        URL url = urlBuilder.buildMovieVideosUrl(id);
        String jsonString = getJSON(url);
        if (jsonString == null) return null;
        return parser.parse(jsonString);
    }
}
