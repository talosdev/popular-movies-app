package app.we.go.movies.dependency;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import app.we.go.movies.constants.TMDB;
import app.we.go.movies.remote.TMDBApiKeyInterceptor;
import app.we.go.movies.remote.TMDBService;
import app.we.go.movies.remote.URLBuilder;
import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
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
    public URLBuilder provideURLBuilder() {
        return new URLBuilder();
    }


    @Provides
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder()
                .setDateFormat(TMDB.DATE_FORMAT)
                .create();
    }


    @Provides
    @Singleton
    public TMDBService provideTMDBService(Gson gson, Interceptor apiKeyInterceptor) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …
        httpClient.addInterceptor(apiKeyInterceptor);

        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TMDB.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

        return retrofit.create(TMDBService.class);
    }



    @Provides
    @Singleton
    public Interceptor provideApiKeyInterceptor() {
        return new TMDBApiKeyInterceptor();
    }
}
