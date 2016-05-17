package app.we.go.movies.remote;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import app.we.go.movies.remote.json.Movie;
import app.we.go.movies.remote.json.Review;
import app.we.go.movies.remote.json.ReviewList;
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

    public static final long MOVIE_ID = 12345;

    public static final String MOVIE_TITLE = "The Movie";

    public static final String MOVIE_OVERVIEW = "A fabulous movie";

    public static final long MOVIE_VOTES = 1000;

    public static final float MOVIE_VOTE_AVG = 4.5f;

    public static final String MOVIE_BACKDROP_PATH = "dummy/movie/backdrop/path";

    public static final String MOVIE_POSTER_PATH = "dummy/movie/poster/path";


    public static final VideoList VIDEOS = new VideoList();
    public static final ReviewList REVIEWS = new ReviewList();


    private static final int NUM_VIDEOS = 4;

    private static final int NUM_REVIEWS = 3;

    public static final Date MOVIE_RELEASE_DATE;
    public static final String MOVIE_RELEASE_DATE_STR = "2015-01-01";



    static {
        DUMMY_MOVIE = new Movie();
        DUMMY_MOVIE.setId(MOVIE_ID);
        DUMMY_MOVIE.setTitle(MOVIE_TITLE);
        DUMMY_MOVIE.setOverview(MOVIE_OVERVIEW);
        DUMMY_MOVIE.setPopularity(7.2f);
        DUMMY_MOVIE.setVoteCount(MOVIE_VOTES);
        DUMMY_MOVIE.setVoteAverage(MOVIE_VOTE_AVG);
        DUMMY_MOVIE.setBackdropPath(MOVIE_BACKDROP_PATH);


        Calendar cal = GregorianCalendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 01);
        cal.set(Calendar.YEAR, 2015);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        MOVIE_RELEASE_DATE = cal.getTime();



        DUMMY_MOVIE.setReleaseDate(MOVIE_RELEASE_DATE);




        List<Video> videos = new ArrayList<>();

        Video v1 = new Video();
        v1.setKey(VideoData.KEY_1);
        v1.setName(VideoData.NAME_1);
        v1.setType(VideoData.TYPE_1);
        videos.add(v1);

        Video v2 = new Video();
        v2.setKey(VideoData.KEY_2);
        v2.setName(VideoData.NAME_2);
        v2.setType(VideoData.TYPE_2);
        videos.add(v2);

        VIDEOS.setVideos(videos);


        List<Review> reviews = new ArrayList<>();

       Review r1 = new Review();
        r1.setContent(ReviewData.REVIEW_1);
        r1.setAuthor(ReviewData.AUTHOR_1);
        reviews.add(r1);

        Review r2 = new Review();
        r2.setContent(ReviewData.REVIEW_2);
        r2.setAuthor(ReviewData.AUTHOR_2);
        reviews.add(r2);


        REVIEWS.setReviews(reviews);
    }

    public class VideoData {
        public static final long MOVIE_ID = 122917L;

        public static final String KEY_1 = "ZSzeFFsKEt4";
        public static final String NAME_1 = "Official Teaser";
        public static final String TYPE_1 = "Teaser";

        public static final String KEY_2 = "Y6Fv5StfAxA";
        public static final String NAME_2 = "Main Trailer";
        public static final String TYPE_2 = "Trailer";
    }

    public class ReviewData {
        public static final String JSON = "{\"id\":293660,\"page\":1,\"results\":[{\"id\":\"56c146cac3a36817f900d5f0\",\"author\":\"huy.duc.eastagile\",\"content\":\"A funny movie with a romantic love story.\\r\\n\\r\\nWade Wilson (Ryan Reynolds) is a former Special Forces operative who now works as a mercenary. His world comes crashing down when evil scientist Ajax (Ed Skrein) tortures, disfigures and transforms him into Deadpool. \\r\\n\\r\\nThe rogue experiment leaves Deadpool with accelerated healing powers and a twisted sense of humor. With help from mutant allies Colossus and Negasonic Teenage Warhead (Brianna Hildebrand), Deadpool uses his new skills to hunt down the man who nearly destroyed his life.\",\"url\":\"http://j.mp/1ohFuvI\"},{\"id\":\"56ca035a9251414a7a0062f0\",\"author\":\"Wong\",\"content\":\"I actually enjoyed the movie so much that i'll recommend it to all my friends, at first i didn't really want to watch it because i'm not into super hero movies at all, but i did anyway, i mean people were talking so much about it i had to see it myself and what an awesome choice i made. The good thing about this movie is that Deadpool is a hero but in a very comedic way, you don't usually expect comedy from a superhero film but this one was full of comedy and the way they treated the plot was amazing, it was there, humor was there in every scene, even when there was fighting or romance or any other scene, the writers managed to add comedy everywhere in a very good way that'll surprisingly make you want to watch it again, and again. Thank you for taking the time read my review and if you're asking yourself if you should watch this movie, it's a definite Yes.\",\"url\":\"http://j.mp/1RVzjsp\"}],\"total_pages\":1,\"total_results\":2}";

        public static final long MOVIE_ID = 293660L;
        public static final String REVIEW_1 = "A funny movie with a romantic love story.\r\n\r\nWade Wilson (Ryan Reynolds) is a former Special Forces operative who now works as a mercenary. His world comes crashing down when evil scientist Ajax (Ed Skrein) tortures, disfigures and transforms him into Deadpool. \r\n\r\nThe rogue experiment leaves Deadpool with accelerated healing powers and a twisted sense of humor. With help from mutant allies Colossus and Negasonic Teenage Warhead (Brianna Hildebrand), Deadpool uses his new skills to hunt down the man who nearly destroyed his life.";
        public static final String REVIEW_2 = "I actually enjoyed the movie so much that i'll recommend it to all my friends, at first i didn't really want to watch it because i'm not into super hero movies at all, but i did anyway, i mean people were talking so much about it i had to see it myself and what an awesome choice i made. The good thing about this movie is that Deadpool is a hero but in a very comedic way, you don't usually expect comedy from a superhero film but this one was full of comedy and the way they treated the plot was amazing, it was there, humor was there in every scene, even when there was fighting or romance or any other scene, the writers managed to add comedy everywhere in a very good way that'll surprisingly make you want to watch it again, and again. Thank you for taking the time read my review and if you're asking yourself if you should watch this movie, it's a definite Yes.";
        public static final String AUTHOR_1 = "huy.duc.eastagile";
        public static final String AUTHOR_2 = "Wong";
    }
}
