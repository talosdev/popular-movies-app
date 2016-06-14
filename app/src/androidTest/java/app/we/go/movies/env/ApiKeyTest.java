package app.we.go.movies.env;

import org.junit.Test;

import app.we.go.movies.BuildConfig;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This tests that the TMDB api key is defined in a system environment property
 *
 * Created by apapad on 12/11/15.
 */
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
