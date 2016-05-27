package app.we.go.movies.dependency;

import android.os.Handler;
import android.os.Looper;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import app.we.go.movies.espresso.ThreadPoolIdlingResource;
import app.we.go.movies.remote.TMDBService;
import app.we.go.movies.remote.URLBuilder;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
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

    @Provides
    @Singleton
    public IdlingResource provideIdlingResource(ThreadPoolExecutor executor) {
        return new ThreadPoolIdlingResource(executor) {
            @Override
            public String getName() {
                return "ThreadPoolIdlingResource";
            }


        };


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

//        return new FakeTMDBServiceSync();


        Retrofit retrofit = new Retrofit.Builder()
                .callbackExecutor(executor)
                .baseUrl("http://example.com").build();

        NetworkBehavior networkBehavior = NetworkBehavior.create();
        networkBehavior.setDelay(30, TimeUnit.SECONDS);
        MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(networkBehavior).build();

        final BehaviorDelegate<TMDBService> delegate = mockRetrofit.create(TMDBService.class);

        return new FakeTMDBServiceAsync(delegate);
    }
}
