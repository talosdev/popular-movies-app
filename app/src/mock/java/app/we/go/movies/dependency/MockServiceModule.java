package app.we.go.movies.dependency;

import app.we.go.movies.remote.FakeTMDBService;
import app.we.go.movies.remote.service.TMDBRetrofitService;
import app.we.go.movies.remote.service.TMDBService;
import retrofit2.Retrofit;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class MockServiceModule extends ServiceModule {


    @Override
    public TMDBRetrofitService provideTMDBRetrofitService(Retrofit retrofit) {
        NetworkBehavior networkBehavior = NetworkBehavior.create();
        // IMPORTANT!!! Network behavior by default uses a non-zero failure percentage
        networkBehavior.setFailurePercent(0);
        MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(networkBehavior).build();

        final BehaviorDelegate<TMDBService> delegate = mockRetrofit.create(TMDBService.class);



        return new FakeTMDBService(delegate);    }
}
