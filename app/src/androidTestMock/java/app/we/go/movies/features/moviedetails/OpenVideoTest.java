package app.we.go.movies.features.moviedetails;

import android.content.Intent;
import android.support.test.espresso.intent.Intents;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import app.we.go.movies.R;
import app.we.go.movies.remote.DummyData;
import app.we.go.movies.remote.URLBuilder;
import app.we.go.movies.model.remote.Video;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@RunWith(Parameterized.class)
public class OpenVideoTest extends BaseMovieDetailsActivityTest {

    @Parameterized.Parameters(name = "{index}: value: {0}")
    public static Iterable<Integer> data() {
        return Arrays.asList(0, 1);
    }

    @Parameterized.Parameter
    public Integer i;

    @Test
    public void testVideoClick() throws Exception {
        Intents.init();

        URLBuilder urlBuilder = new URLBuilder();

        onView(withId(R.id.details_pager)).perform(swipeLeft());
        onView(withId(R.id.details_pager)).perform(swipeLeft());


        onData(is(instanceOf(Video.class))).
                inAdapterView(withId(R.id.videos_list)).
                atPosition(i).
                onChildView(withId(R.id.videoDetails)).
                perform(click());

        intended(allOf(
                hasAction(Intent.ACTION_VIEW),
                hasData(urlBuilder.buildYoutubeUri(DummyData.VIDEO_KEYS[i]))));

        Intents.release();

    }
}
