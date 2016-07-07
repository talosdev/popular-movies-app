package app.we.go.movies.dependency;

import android.os.AsyncTask;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Override-module of {@link SchedulersModule} that uses the AsyncTask
 * thread pool (for Espresso testing)
 *
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class AndroidTestSchedulersModule extends SchedulersModule {


    public AndroidTestSchedulersModule() {

    }


    @Override
    public Scheduler provideSubscribeOnScheduler() {
        return Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR);
    }

}