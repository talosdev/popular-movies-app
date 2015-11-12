package talosdev.movies.config;

import android.test.AndroidTestCase;

import talosdev.movies.BuildConfig;

/**
 * Created by apapad on 12/11/15.
 */
public class ApiKeyTest extends AndroidTestCase {


    /**
     * Test that the Api Key is found in the environment variable that is
     * referenced in the gradle file
     */
    public void testApiKey() {
        String apiKey = BuildConfig.TMDB_API_KEY;
        assertNotNull(apiKey);
        assertTrue(apiKey.length() > 0);
    }

}
