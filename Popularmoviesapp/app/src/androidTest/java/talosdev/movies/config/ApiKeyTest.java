package talosdev.movies.config;

import android.test.AndroidTestCase;

import talosdev.movies.BuildConfig;

import static org.junit.Assert.assertNotEquals;

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
        System.out.println("xxx" + apiKey);
        assertNotEquals("apikey is equals to the \"null\" string. Make sure the env variable is configured", "null", apiKey);
        assertNotNull(apiKey);
        assertTrue(apiKey.length() > 0);
    }

}
