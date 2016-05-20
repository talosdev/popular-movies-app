package app.we.go.movies.remote;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by apapad on 13/11/15.
 */
public class URLBuilderTest {
    String backdrop = "/a.jpg";
    String BACKDROP_URL_300 = "http://image.tmdb.org/t/p/w300/a.jpg";
    String BACKDROP_URL_780 = "http://image.tmdb.org/t/p/w780/a.jpg";
    String BACKDROP_URL_1280 = "http://image.tmdb.org/t/p/w1280/a.jpg";

    @Test
    public void testBackrdropURLs() throws Exception {
        assertThat(URLBuilder.buildBackdropPath(backdrop, 1600)).
                isEqualTo(BACKDROP_URL_1280);

        assertThat(URLBuilder.buildBackdropPath(backdrop, 1080)).
                isEqualTo(BACKDROP_URL_1280);

        assertThat(URLBuilder.buildBackdropPath(backdrop, 500)).
                isEqualTo(BACKDROP_URL_780);

        assertThat(URLBuilder.buildBackdropPath(backdrop, 280)).
                isEqualTo(BACKDROP_URL_300);

        assertThat(URLBuilder.buildBackdropPath(backdrop, 780)).
                isEqualTo(BACKDROP_URL_780);

    }


    // TODO test the posterURLs
    // Need to mock the context


}