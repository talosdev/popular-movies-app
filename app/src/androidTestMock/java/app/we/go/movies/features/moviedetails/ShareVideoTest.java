package app.we.go.movies.features.moviedetails;

import android.content.Intent;
import android.support.test.espresso.intent.Intents;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import app.we.go.movies.R;
import app.we.go.movies.model.remote.Video;
import app.we.go.movies.remote.DummyData;
import app.we.go.movies.remote.URLBuilder;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@RunWith(Parameterized.class)
public class ShareVideoTest extends BaseMovieDetailsActivityTest{

    @Parameterized.Parameters(name = "{index}: value: {0}")
    public static Iterable<Integer> data() {
        return Arrays.asList(0, 1);
    }

    @Parameterized.Parameter
    public Integer i;

    @Test
    public void testVideoShare() throws Exception {
        Intents.init();

        URLBuilder urlBuilder = new URLBuilder();
        onView(withId(R.id.details_pager)).perform(swipeLeft());
        onView(withId(R.id.details_pager)).perform(swipeLeft());


        onData(is(instanceOf(Video.class))).
                inAdapterView(withId(R.id.videos_list)).
                atPosition(i).
                onChildView(withId(R.id.share_button)).
                perform(click());

        intended(allOf(hasAction(Intent.ACTION_CHOOSER),
                hasExtra(is(Intent.EXTRA_INTENT),
                        allOf( hasAction(Intent.ACTION_SEND),
                                hasExtra(Intent.EXTRA_TEXT, urlBuilder.buildYoutubeUri(DummyData.VIDEO_KEYS[i])),
                                hasExtra(Intent.EXTRA_SUBJECT, DummyData.VIDEO_NAMES[i]) ))));

        Intents.release();
    }

}
