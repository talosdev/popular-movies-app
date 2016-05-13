package app.we.go.movies.movies.remote;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import app.we.go.movies.remote.json.Video;
import app.we.go.movies.remote.json.VideoList;
import app.we.go.movies.remote.json.VideosJSONParser;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by apapad on 1/03/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class VideosJSONParserTest {
    private String json = "{\"id\":122917,\"results\":[{\"id\":\"53d6b3540e0a26033a000012\",\"iso_639_1\":\"en\",\"key\":\"ZSzeFFsKEt4\",\"name\":\"Official Teaser\",\"site\":\"YouTube\",\"size\":360,\"type\":\"Teaser\"},{\"id\":\"545bd86f0e0a261fa900258b\",\"iso_639_1\":\"en\",\"key\":\"Y6Fv5StfAxA\",\"name\":\"Main Trailer\",\"site\":\"YouTube\",\"size\":1080,\"type\":\"Trailer\"}]}";
    public static final String KEY_1 = "ZSzeFFsKEt4";
    public static final String NAME_1 = "Official Teaser";
    public static final String TYPE_1 = "Teaser";

    public static final String KEY_2 = "Y6Fv5StfAxA";
    public static final String NAME_2 = "Main Trailer";
    public static final String TYPE_2 = "Trailer";



    private static VideosJSONParser parser;

    @BeforeClass
    public static void setUp() {
        parser = new VideosJSONParser();
    }


    @Test
    public void testParseReviews() throws Exception {
        VideoList videoList = parser.parse(json);
        List<Video> videos = videoList.getVideos();

        assertThat(videos.size()).isEqualTo(2);

        Video firstVideo = videos.get(0);
        assertThat(firstVideo.getKey()).isEqualTo(KEY_1);
        assertThat(firstVideo.getName()).isEqualTo(NAME_1);
        assertThat(firstVideo.getType()).isEqualTo(TYPE_1);

        Video secondVideo = videos.get(1);
        assertThat(secondVideo.getKey()).isEqualTo(KEY_2);
        assertThat(secondVideo.getName()).isEqualTo(NAME_2);
        assertThat(secondVideo.getType()).isEqualTo(TYPE_2);

    }

    @Test
    public void testBackAndForth() throws Exception {
        VideoList videosList = parser.parse(json);
        String newJson = parser.toJson(videosList);
        VideoList parsedVideoList = parser.parse(newJson);
        assertThat(parsedVideoList.getVideos()).containsOnlyElementsOf(videosList.getVideos());

    }
}