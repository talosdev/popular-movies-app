package app.we.go.movies.dependency;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.inject.Named;
import javax.inject.Singleton;

import app.we.go.movies.constants.TMDB;
import app.we.go.movies.remote.TMDBApiKeyInterceptor;
import app.we.go.movies.remote.TMDBErrorParser;
import app.we.go.movies.remote.URLBuilder;
import app.we.go.movies.remote.service.TMDBRetrofitService;
import app.we.go.movies.remote.service.TMDBService;
import app.we.go.movies.remote.service.TMDBServiceImpl;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Scheduler;

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
    @Named("rx")
    public CallAdapter.Factory provideRxCallAdapterFactory() {
        return RxJavaCallAdapterFactory.create();
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


    /**
     * A {@linkplain retrofit2.CallAdapter.Factory} that applies a transformer to the observables
     * that the retrofit service returns.
     * @param rxFactory
     * @param transformer
     * @return
     */
    @Provides
    @Singleton
    @Named("transformer")
    public CallAdapter.Factory provideCallAdapterFactory(
            @Named("rx") final CallAdapter.Factory rxFactory,
            final Observable.Transformer<Response<?>, Response<?>> transformer) {
        return new CallAdapter.Factory() {
            @Override
            public CallAdapter<Observable<Response<?>>> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
                final CallAdapter<Observable<Response<?>>> rxCallAdapter =
                        (CallAdapter<Observable<Response<?>>>)
                                rxFactory.get(returnType, annotations, retrofit);

                // If the rxCallAdapter can't process this, then we skip it too
                if (rxCallAdapter == null) {
                    return null;
                }

                return new CallAdapter<Observable<Response<?>>>() {
                    @Override
                    public Type responseType() {
                        return rxCallAdapter.responseType();
                    }

                    @Override
                    public <R> Observable<Response<?>> adapt(Call<R> call) {
                        return rxCallAdapter.adapt(call).
                                compose(transformer);
                    }
                };
            }
        };
    }


    @Provides
    @Singleton
    public Retrofit provideRetrofit(Gson gson,
                                    OkHttpClient okHttpClient,
                                    @Named("transformer") CallAdapter.Factory transformerCallAdapterFactory) {

        return new Retrofit.Builder()
                .baseUrl(TMDB.BASE_URL)
                .addCallAdapterFactory(transformerCallAdapterFactory)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

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
