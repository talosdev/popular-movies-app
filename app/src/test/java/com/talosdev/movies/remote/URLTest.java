package com.talosdev.movies.remote;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by apapad on 13/11/15.
 */
public class URLTest {
    String backdrop = "/a.jpg";
    String URL_300 = "http://image.tmdb.org/t/p/w300/a.jpg";
    String URL_780 = "http://image.tmdb.org/t/p/w780/a.jpg";
    String URL_1280 = "http://image.tmdb.org/t/p/w1280/a.jpg";

    @Test
    public void testBackrdropURLs() throws Exception {
        assertThat(URLBuilder.buildBackdropPath(backdrop, 1080)).
                isEqualTo(URL_780);

        assertThat(URLBuilder.buildBackdropPath(backdrop, 1600)).
                isEqualTo(URL_1280);

        assertThat(URLBuilder.buildBackdropPath(backdrop, 500)).
                isEqualTo(URL_300);

        assertThat(URLBuilder.buildBackdropPath(backdrop, 280)).
                isEqualTo(URL_300);

        assertThat(URLBuilder.buildBackdropPath(backdrop, 780)).
                isEqualTo(URL_780);


    }


}