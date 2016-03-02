package app.we.go.movies.remote;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import app.we.go.movies.remote.json.Video;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by apapad on 2/03/16.
 */
@RunWith(AndroidJUnit4.class)
public class MovieVideosFetcherTest {
    private VideosFetcher fetcher;
    public static final String KEY_1 = "ZSzeFFsKEt4";
    public static final String NAME_1 = "Official Teaser";
    public static final String TYPE_1 = "Teaser";

    public static final String KEY_2 = "Y6Fv5StfAxA";
    public static final String NAME_2 = "Main Trailer";
    public static final String TYPE_2 = "Trailer";

    @Before
    public void setUp() {
        fetcher = new VideosFetcher();
    }


    /**
     * Contacts the API, gets the
     * @throws Exception
     */
    @Test
    public void testFetch() throws Exception {
        List<Video> videos = fetcher.fetch(122917).videos;

        assertThat(videos.size()).isGreaterThanOrEqualTo(2);

        Video v1 = videos.get(0);
        assertThat(v1.key).isEqualTo(KEY_1);
        assertThat(v1.name).isEqualTo(NAME_1);
        assertThat(v1.type).isEqualTo(TYPE_1);

        Video v2 = videos.get(1);
        assertThat(v2.key).isEqualTo(KEY_2);
        assertThat(v2.name).isEqualTo(NAME_2);
        assertThat(v2.type).isEqualTo(TYPE_2);

    }

}
