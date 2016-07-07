package app.we.go.movies.dependency;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Override-module for {@link SchedulersModule} that makes the observables synchronous,
 * for testing of the presenters.
 *
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class TestSchedulersModule extends SchedulersModule {


    @Override
    public Scheduler provideSubscribeOnScheduler() {
        return Schedulers.immediate();
    }


    @Override
    public Scheduler provideObserveOnScheduler() {
        return Schedulers.immediate();
    }

}