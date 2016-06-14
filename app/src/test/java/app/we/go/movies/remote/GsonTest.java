package app.we.go.movies.remote;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;

import java.net.URL;

import app.we.go.movies.TestData;
import app.we.go.movies.dependency.ApplicationModule;
import app.we.go.movies.model.remote.Movie;
import app.we.go.movies.model.remote.MovieList;
import app.we.go.framework.util.StringUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the serialization.
 * Not
 *
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class GsonTest {

    private Gson gson;
    private String json;

    @Before
    public void setUp() throws Exception {
        gson = (new ApplicationModule()).provideGson();
        URL resource = this.getClass().getClassLoader().getResource("movie-list-empty-date.json");
        json = StringUtils.readInputStreamToString(resource.openStream());
    }


    @Test
    public void testGsonEmptyDate() throws Exception {
        MovieList movieList = gson.fromJson(json, MovieList.class);

        assertThat(movieList.getMovies()).hasSize(1);
        Movie m = movieList.getMovies().get(0);
        assertThat(m.getReleaseDate()).isNull();
        assertThat(m.getTitle()).isEqualTo(TestData.MOVIE_TITLE);
        // etc...
    }
}
