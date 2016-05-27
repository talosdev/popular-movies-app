package app.we.go.movies.dependency;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;

import javax.inject.Singleton;

import app.we.go.movies.constants.TMDB;
import app.we.go.movies.remote.TMDBApiKeyInterceptor;
import app.we.go.movies.remote.TMDBErrorParser;
import app.we.go.movies.remote.TMDBRetrofitService;
import app.we.go.movies.remote.TMDBService;
import app.we.go.movies.remote.TMDBServiceImpl;
import app.we.go.movies.remote.URLBuilder;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
@Module
public class ServiceModule {

    public ServiceModule() {
    }

    @Provides
    @Singleton
    public URLBuilder provideUrlBuilder() {
        return new URLBuilder();
    }


    @Provides
    @Singleton
    public CallAdapter.Factory provideCallAdapterFactory() {
        return RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
    }


    @Provides
    @Singleton
    public Retrofit provideRetrofit(Gson gson,
                                    OkHttpClient okHttpClient,
                                    CallAdapter.Factory callAdapterFactory) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TMDB.BASE_URL)
                .addCallAdapterFactory(callAdapterFactory)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        return retrofit;

    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(TMDBApiKeyInterceptor apiKeyInterceptor) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(apiKeyInterceptor);

        // add logging as last interceptor
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        httpClient.addInterceptor(logging);

        // stetho interceptor
        httpClient.addNetworkInterceptor(new StethoInterceptor());

        return httpClient.build();
    }


    @Provides
    @Singleton
    public TMDBRetrofitService provideTMDBRetrofitService(Retrofit retrofit) {
        return retrofit.create(TMDBRetrofitService.class);
    }


    @Provides
    @Singleton
    public TMDBService provideTMDBService(TMDBRetrofitService retrofitService,
                                          TMDBErrorParser parser) {
        return new TMDBServiceImpl(retrofitService, parser);

    }

    @Provides
    @Singleton
    public TMDBErrorParser provideTMDBErrorParser(Retrofit retrofit) {
        return new TMDBErrorParser(retrofit);
    }

}
