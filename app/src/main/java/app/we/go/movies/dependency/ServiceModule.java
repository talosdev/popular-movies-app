package app.we.go.movies.dependency;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;

import javax.inject.Singleton;

import app.we.go.movies.constants.TMDB;
import app.we.go.movies.remote.TMDBApiKeyInterceptor;
import app.we.go.movies.remote.TMDBErrorParser;
import app.we.go.movies.remote.service.TMDBRetrofitService;
import app.we.go.movies.remote.service.TMDBService;
import app.we.go.movies.remote.service.TMDBServiceImpl;
import app.we.go.movies.remote.URLBuilder;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observable.Transformer;
import rx.android.schedulers.AndroidSchedulers;
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
        return RxJavaCallAdapterFactory.create();
    }

    @Provides
    @Singleton
    public Transformer<Response<?>, Response<?>> provideTransformer() {

        return new Transformer<Response<?>, Response<?>>() {


            @Override
            public Observable<Response<?>> call(Observable<Response<?>> obs) {
                return obs.
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread());
            }
        };

    }


    @Provides
    @Singleton
    public Retrofit provideRetrofit(Gson gson,
                                    OkHttpClient okHttpClient,
                                    CallAdapter.Factory callAdapterFactory) {

        return new Retrofit.Builder()
                .baseUrl(TMDB.BASE_URL)
                .addCallAdapterFactory(callAdapterFactory)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

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
                                          TMDBErrorParser parser,
                                          Transformer<Response<?>, Response<?>>  transformer) {

        return new TMDBServiceImpl(retrofitService, parser, transformer);

    }

    @Provides
    @Singleton
    public TMDBErrorParser provideTMDBErrorParser(Retrofit retrofit) {
        return new TMDBErrorParser(retrofit);
    }

}
