package com.talosdev.movies.remote;

import com.talosdev.movies.TestUtils;
import com.talosdev.movies.remote.json.Movie;
import com.talosdev.movies.remote.json.MovieJSONParser;
import com.talosdev.movies.remote.json.MovieList;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by apapad on 13/11/15.
 */
public class MovieJSONParserTest {

    private String json="{\"adult\":false,\"backdrop_path\":\"/wVTYlkKPKrljJfugXN7UlLNjtuJ.jpg\",\"genre_ids\":[28,12,80],\"id\":206647,\"original_language\":\"en\",\"original_title\":\"Spectre\",\"overview\":\"A cryptic message from Bond’s past sends him on a trail to uncover a sinister organization. While M battles political forces to keep the secret service alive, Bond peels back the layers of deceit to reveal the terrible truth behind SPECTRE.\",\"release_date\":\"2015-11-06\",\"poster_path\":\"/1n9D32o30XOHMdMWuIT4AaA5ruI.jpg\",\"popularity\":57.231904,\"title\":\"Spectre\",\"video\":false,\"vote_average\":6.7,\"vote_count\":453}";
    private String OVERVIEW = "A cryptic message from Bond’s past sends him on a trail to uncover a sinister organization. While M battles political forces to keep the secret service alive, Bond peels back the layers of deceit to reveal the terrible truth behind SPECTRE.";
    private static MovieJSONParser parser;

    @Test
    public void testParseMovie() throws Exception {
        Movie parsedMovie = parser.parseMovie(json);
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
        assertEquals("Jurassic World", movieList.movies.get(1).title);
        assertEquals(1162, movieList.movies.get(19).voteCount);

    }


    private void verifyMovie(Movie movie) throws ParseException {
        assertNotNull(movie);
        assertEquals("Spectre", movie.title);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        assertThat(movie.releaseDate).isInSameDayAs(sdf.parse("2015-11-06"));

        assertEquals(206647, movie.id);
        assertEquals(6.7f, movie.voteAverage, TestUtils.EPSILON);
        assertEquals(453, movie.voteCount);
        assertEquals(57.231904f, movie.popularity, TestUtils.EPSILON);
        assertEquals("/1n9D32o30XOHMdMWuIT4AaA5ruI.jpg", movie.posterPath);
        assertEquals("/wVTYlkKPKrljJfugXN7UlLNjtuJ.jpg", movie.backdropPath);
        assertEquals(OVERVIEW, movie.overview);
    }
}