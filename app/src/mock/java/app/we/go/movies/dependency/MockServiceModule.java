package app.we.go.movies.dependency;

import android.os.AsyncTask;

import javax.inject.Singleton;

import app.we.go.movies.remote.FakeTMDBService;
import app.we.go.movies.remote.FakeURLBuilder;
import app.we.go.movies.remote.service.TMDBService;
import app.we.go.movies.remote.URLBuilder;
import dagger.Module;
import dagger.Provides;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;
import rx.Observable;
import rx.Observable.Transformer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@Module
public class MockServiceModule {

    @Provides
    @Singleton
    public URLBuilder provideUrlBuilder() {
        return new FakeURLBuilder();
    }


    @Provides
    @Singleton
    public TMDBService provideService() {
        return FakeTmdbServiceAsyncFactory.getInstance(false);
    }


    public static class FakeTmdbServiceAsyncFactory {

        private static FakeTMDBService INSTANCE;

        private static final Transformer<Response<?>, Response<?>> syncTransformer =
                new Transformer<Response<?>, Response<?>>() {
                    @Override
                    public Observable<Response<?>> call(Observable<Response<?>> responseObservable) {
                        return responseObservable;
                    }
                };

        private static final Transformer<Response<?>, Response<?>> asyncTransformer =
                new Transformer<Response<?>, Response<?>>() {
                    @Override
                    public Observable<Response<?>> call(Observable<Response<?>> responseObservable) {
                        return responseObservable.
                                observeOn(AndroidSchedulers.mainThread()).
                                subscribeOn(Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR));
                    }
                };


        /**
         * This factory method can offer two flavors of the FakeTMDBService: a sync one
         * to be used in JUnit tests, with no dependencies on the Android framework,
         * and an async one, to be used in instrumentation tests.
         * @param sync
         * @return
         */
        public static TMDBService getInstance(boolean sync) {
            if (INSTANCE == null) {
                Retrofit retrofit = new Retrofit.Builder().
                        addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                        baseUrl("http://example.com").build();

                NetworkBehavior networkBehavior = NetworkBehavior.create();
                // IMPORTANT!!! Network behavior by default uses a non-zero failure percentage
                networkBehavior.setFailurePercent(0);
                MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
                        .networkBehavior(networkBehavior).build();

                final BehaviorDelegate<TMDBService> delegate = mockRetrofit.create(TMDBService.class);

                INSTANCE = new FakeTMDBService(delegate, sync ? syncTransformer : asyncTransformer);

            }
            return INSTANCE;
        }



    }
}
