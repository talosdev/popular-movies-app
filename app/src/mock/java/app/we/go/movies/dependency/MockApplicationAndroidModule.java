package app.we.go.movies.dependency;

import android.content.Context;
import android.os.AsyncTask;

import javax.inject.Named;
import javax.inject.Singleton;

import app.we.go.movies.helpers.SharedPreferencesHelper;
import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@Module
public class MockApplicationAndroidModule {

    private Context context;

    public MockApplicationAndroidModule(Context context) {
        this.context = context;
    }



    // TODO think about this
    @Provides
    @Singleton
    public SharedPreferencesHelper provideSharedPreferencesHelper() {
        return new SharedPreferencesHelper(context);
    }


    @Provides
    @Singleton
    @Named("observeOn")
    public Scheduler provideObserveOnScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Singleton
    @Named("subscribeOn")
    public Scheduler provideSubscribeOnScheduler() {
        return Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR);
    }


}