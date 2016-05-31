package app.we.go.movies.env;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import app.we.go.movies.BuildConfig;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(apiKey).
                as("apikey is equals to the \"null\" string. Make sure the env variable is configured").
                isNotEqualTo("null");

        assertThat(apiKey).
                as("apikey is null").
                isNotNull().
                as("apikey is empty").
                isNotEmpty();

    }

}
