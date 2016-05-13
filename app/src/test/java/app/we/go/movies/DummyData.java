package app.we.go.movies;

import java.util.ArrayList;
import java.util.List;

import app.we.go.movies.remote.json.Movie;
import app.we.go.movies.remote.json.Video;
import app.we.go.movies.remote.json.VideoList;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class DummyData {

    public static final String MOCK_BASE_URL = "http://www.example.com";


    public static Movie DUMMY_MOVIE;

    public static final long INEXISTENT_MOVIE_ID = 9999L;

    public static final long MOVIE_ID_CAUSES_SERVER_ERROR = 666L;

    public static final long DUMMY_MOVIE_ID = 12345;

    public static final String DUMMY_MOVIE_TITLE = "The Movie";

    public static final String DUMMY_MOVIE_DESCRIPTION = "A fabulous movie";

    public static final long DUMMY_MOVIE_VOTES = 1000;

    public static final float DUMMY_MOVIE_VOTE_AVG = 4.5f;

    public static final String DUMMY_MOVIE_BACKDROP_PATH = "dummy/movie/backdrop/path";

    public static final VideoList VIDEOS = new VideoList();


    private static final int NUM_VIDEOS = 4;

    static {
        DUMMY_MOVIE = new Movie();
        DUMMY_MOVIE.setId(DUMMY_MOVIE_ID);
        DUMMY_MOVIE.setTitle(DUMMY_MOVIE_TITLE);
        DUMMY_MOVIE.setOverview(DUMMY_MOVIE_DESCRIPTION);
        DUMMY_MOVIE.setPopularity(7.2f);
        DUMMY_MOVIE.setVoteCount(DUMMY_MOVIE_VOTES);
        DUMMY_MOVIE.setVoteAverage(DUMMY_MOVIE_VOTE_AVG);
        DUMMY_MOVIE.setBackdropPath(DUMMY_MOVIE_BACKDROP_PATH);


        List<Video> videos = new ArrayList<>();

        for (int i=0; i<NUM_VIDEOS; i++) {
            Video v = new Video();
            v.setId("DummyVideo_" + i);
            v.setKey("key0000" + i);
            v.setName("Video_"+i);
            v.setType("Dummy Trailer");


            videos.add(v);
        }

        VIDEOS.setVideos(videos);
    }
}
