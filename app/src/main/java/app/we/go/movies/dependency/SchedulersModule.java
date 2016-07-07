package app.we.go.movies.dependency;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Response;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@Module
public class SchedulersModule {

    public SchedulersModule() {
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
        return Schedulers.io();
    }

    // can be shared between prod and mock
    @Provides
    @Singleton
    public Observable.Transformer<Response<?>, Response<?>> provideSchedulersTransformer(
            @Named("observeOn") final Scheduler observeOnScheduler,
            @Named("subscribeOn") final Scheduler subscribeOnOnScheduler) {
        return new Observable.Transformer<Response<?>, Response<?>>() {
            @Override
            public Observable<Response<?>> call(Observable<Response<?>> responseObservable) {
                return responseObservable.
                        observeOn(observeOnScheduler).
                        subscribeOn(subscribeOnOnScheduler);
            }
        };
    }
}