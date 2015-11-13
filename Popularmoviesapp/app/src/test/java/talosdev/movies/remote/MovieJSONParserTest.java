package talosdev.movies.remote;

import org.junit.Test;

import talosdev.movies.TestUtils;

import static org.junit.Assert.*;

/**
 * Created by apapad on 13/11/15.
 */
public class MovieJSONParserTest {

    private String json="{\"adult\":false,\"backdrop_path\":\"/wVTYlkKPKrljJfugXN7UlLNjtuJ.jpg\",\"genre_ids\":[28,12,80],\"id\":206647,\"original_language\":\"en\",\"original_title\":\"Spectre\",\"overview\":\"A cryptic message from Bond’s past sends him on a trail to uncover a sinister organization. While M battles political forces to keep the secret service alive, Bond peels back the layers of deceit to reveal the terrible truth behind SPECTRE.\",\"release_date\":\"2015-11-06\",\"poster_path\":\"/1n9D32o30XOHMdMWuIT4AaA5ruI.jpg\",\"popularity\":57.231904,\"title\":\"Spectre\",\"video\":false,\"vote_average\":6.7,\"vote_count\":453}";
    private String OVERVIEW = "A cryptic message from Bond’s past sends him on a trail to uncover a sinister organization. While M battles political forces to keep the secret service alive, Bond peels back the layers of deceit to reveal the terrible truth behind SPECTRE.";
    @Test
    public void testParse() throws Exception {
        MovieJSONParser parser = new MovieJSONParser();
        Movie parsedMovie = parser.parse(json);

        assertNotNull(parsedMovie);
        assertEquals("Spectre", parsedMovie.title);
        assertEquals("2015-11-06", parsedMovie.releaseDate);
        assertEquals(206647, parsedMovie.id);
        assertEquals(6.7f, parsedMovie.voteAverage, TestUtils.EPSILON);
        assertEquals(453, parsedMovie.voteCount);
        assertEquals(57.231904f, parsedMovie.popularity, TestUtils.EPSILON);
        assertEquals("/1n9D32o30XOHMdMWuIT4AaA5ruI.jpg", parsedMovie.posterPath);
        assertEquals(OVERVIEW, parsedMovie.overview);
    }
}