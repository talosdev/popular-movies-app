package app.we.go.movies.dependency;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import app.we.go.movies.remote.TMDBService;
import app.we.go.movies.remote.URLBuilder;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@Module
public class MockServiceModule  {

    @Provides
    @Singleton
    public URLBuilder provideUrlBuilder() {
        return new FakeURLBuilder();
    }


    @Provides
    @Singleton
    public ThreadPoolExecutor provideThreadPoolExecutor() {

        final Handler handler = new Handler(Looper.getMainLooper());

        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1)) {
            @Override
            public void execute(Runnable command) {
//                handler.post(command);
                command.run();
            }
        };
        return executor;
    }



    /**
     * https://github.com/square/retrofit/issues/1081
     *
     * For IdlingResource to work OK we need to do all this....
     * @param executor
     * @return
     */
    @Provides
    @Singleton
    public TMDBService provideServiceModule(ThreadPoolExecutor executor) {

        return FakeTmdbServiceAsyncFactory.getInstance(executor);
    }



    public static class FakeTmdbServiceAsyncFactory {

        private static FakeTMDBServiceAsync INSTANCE;

        public static TMDBService getInstance(ThreadPoolExecutor executor) {
            if (INSTANCE == null) {
                Retrofit retrofit = new Retrofit.Builder().
                   //     callbackExecutor(executor).
                        addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                        baseUrl("http://example.com").build();

                NetworkBehavior networkBehavior = NetworkBehavior.create();
            //    networkBehavior.setDelay(30, TimeUnit.SECONDS);
                MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
                        .networkBehavior(networkBehavior).build();

                final BehaviorDelegate<TMDBService> delegate = mockRetrofit.create(TMDBService.class);

                INSTANCE = new FakeTMDBServiceAsync(delegate);

            }
            return INSTANCE;
        }

    }
}
