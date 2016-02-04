package com.talosdev.movies.contract;

import android.net.Uri;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by apapad on 2016-01-15.
 */
public class MoviesContractTest {

    @Test
    public void testBuildDetailsUri() {
        assertThat(MoviesContract.MovieEntry.buildDetailsUri(1000l)).
                isEqualTo(Uri.parse("content://com.talosdev.movies/details/1000"));
    }

}