package app.we.go.movies.util;

import java.util.List;

import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class RxTestingUtils {


    /**
     * Assert that the observable has completed without errors and that the test subscriber
     * has only received one element.
     * @param testSubscriber
     * @param <T> T
     *            The unique element
     * @return
     */
    public static <T> T getUniqueOnNextEvent(TestSubscriber<T> testSubscriber) {
        testSubscriber.assertNoErrors();
        List<T> onNextEvents = testSubscriber.getOnNextEvents();

        assertThat(onNextEvents).hasSize(1);
        return onNextEvents.get(0);
    }
}
