package app.we.go.movies.dependency;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import app.we.go.movies.constants.TMDB;
import app.we.go.movies.remote.URLBuilder;
import dagger.Module;
import dagger.Provides;

/**
 * Created by apapad on 9/03/16.
 */
@Module
public class ApplicationModule {


    public ApplicationModule() {
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
    public URLBuilder provideUrlBuilder() {
        return new URLBuilder();
    }




}
