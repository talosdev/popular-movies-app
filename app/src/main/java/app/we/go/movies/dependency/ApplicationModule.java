package app.we.go.movies.dependency;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import app.we.go.movies.constants.TMDB;
import app.we.go.movies.remote.TMDBApiKeyInterceptor;
import app.we.go.movies.remote.TMDBErrorParser;
import app.we.go.movies.remote.TMDBService;
import app.we.go.movies.remote.URLBuilder;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by apapad on 9/03/16.
 */
@Module
public class ApplicationModule {


    @Provides
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder()
                .setDateFormat(TMDB.DATE_FORMAT)
                .create();
    }


    @Provides
    @Singleton
    public Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TMDB.BASE_URL)
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

        return httpClient.build();
    }


    @Provides
    @Singleton
    public TMDBService provideTMDBService(Retrofit retrofit) {
        return retrofit.create(TMDBService.class);
    }

    @Provides
    @Singleton
    public URLBuilder provideUrlBuilder() {
        return new URLBuilder();
    }



    @Provides
    @Singleton
    public TMDBErrorParser provideTMDBErrorParser(Retrofit retrofit) {
        return new TMDBErrorParser(retrofit);
    }
}
