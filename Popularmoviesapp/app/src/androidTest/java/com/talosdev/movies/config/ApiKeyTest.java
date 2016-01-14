package com.talosdev.movies.config;

import android.support.test.runner.AndroidJUnit4;

import com.talosdev.movies.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by apapad on 12/11/15.
 */
@RunWith(AndroidJUnit4.class)
public class ApiKeyTest  {


    /**
     * Test that the Api Key is found in the environment variable that is
     * referenced in the gradle file
     */
    @Test
    public void testApiKey() {
        String apiKey = BuildConfig.TMDB_API_KEY;
        assertNotEquals("apikey is equals to the \"null\" string. Make sure the env variable is configured", "null", apiKey);
        assertNotNull(apiKey);
        assertTrue(apiKey.length() > 0);
    }

}
