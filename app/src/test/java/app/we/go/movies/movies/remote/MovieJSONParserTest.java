package app.we.go.movies.remote;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import app.we.go.movies.DummyData;
import app.we.go.movies.TestData;
import app.we.go.movies.TestUtils;
import app.we.go.movies.constants.TMDB;
import app.we.go.movies.remote.json.Movie;
import app.we.go.movies.remote.json.MovieJSONParser;
import app.we.go.movies.remote.json.MovieList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * TODO
 * when fetching movie list is migrated to retrofit, remove this
 *
 * Created by apapad on 13/11/15.
 */
public class MovieJSONParserTest {

    private static MovieJSONParser parser;

    @Test
    public void testParseMovie() throws Exception {
        Movie parsedMovie = parser.parseMovie(TestData.MOVIE_JSON);
        verifyMovie(parsedMovie);
    }

    @BeforeClass
    public static void setUp() {
        parser = new MovieJSONParser();
    }

    @Test
    public void testParseMovieList() throws Exception {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("movie-list.json");
        String s = TestUtils.convertStreamToString(resourceAsStream);

        MovieList movieList = parser.parseMovieList(s);

        assertNotNull(movieList);
        assertEquals(1, movieList.page);
        assertEquals(12520, movieList.totalPages);
        assertEquals(250384, movieList.totalResults);
        assertEquals(20, movieList.movies.size());

        // Verify all fields of the first movie
        Movie firstMovie = movieList.movies.get(0);
        verifyMovie(firstMovie);

        // Verify some fields from the other movies
        assertEquals("Jurassic World", movieList.movies.get(1).getTitle());
        assertEquals(1162, movieList.movies.get(19).getVoteCount());

    }


    private void verifyMovie(Movie movie) throws ParseException {
        assertNotNull(movie);
        assertEquals("Spectre", movie.getTitle());

        SimpleDateFormat sdf = new SimpleDateFormat(TMDB.DATE_FORMAT);
        assertThat(movie.getReleaseDate()).isInSameDayAs(sdf.parse(TestData.MOVIE_RELEASE_DATE));

        assertEquals(206647, movie.getId());
        assertEquals(6.7f, movie.getVoteAverage(), TestUtils.EPSILON);
        assertEquals(453, movie.getVoteCount());
        assertEquals(57.231904f, movie.getPopularity(), TestUtils.EPSILON);
        assertEquals("/1n9D32o30XOHMdMWuIT4AaA5ruI.jpg", movie.getPosterPath());
        assertEquals("/wVTYlkKPKrljJfugXN7UlLNjtuJ.jpg", movie.getBackdropPath());
        assertEquals(TestData.MOVIE_OVERVIEW, movie.getOverview());
    }

    @Test
    public void testEmptyDate() throws Exception{
        Movie parsedMovie = parser.parseMovie(TestData.MOVIE_JSON_EMPTY_DATE);
        assertThat(parsedMovie).isNotNull();
        assertThat(parsedMovie.getTitle()).isEqualTo("Spectre");
        assertThat(parsedMovie.getReleaseDate()).isNull();
    }

    @Test
    public void testBackAndForth() throws Exception {
//        String json = parser.toJson(DummyData.DUMMY_MOVIE);
//        Movie m = parser.parseMovie(json);
//        assertThat(m).isEqualToComparingFieldByField(DummyData.DUMMY_MOVIE);
//        assertThat(m).isEqualTo(DummyData.DUMMY_MOVIE);
    }
}