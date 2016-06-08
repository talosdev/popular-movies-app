package app.we.go.movies.dependency;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Singleton;

import app.we.go.framework.mvp.presenter.PresenterCache;
import app.we.go.movies.constants.TMDB;
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

        GsonBuilder gsonBuilder = new GsonBuilder();

        // Custom Date adapter that accepts empty dates
        // (catches the exception and returns null)
        gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            DateFormat df = new SimpleDateFormat(TMDB.DATE_FORMAT);
            @Override
            public Date deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
                    throws JsonParseException {
                try {
                    return df.parse(json.getAsString());
                } catch (ParseException e) {
                    return null;
                }
            }
        });
        return gsonBuilder.create();
    }



    @Provides
    @Singleton
    public PresenterCache providePresenterCache() {
        return new PresenterCache();
    }









}
