package com.talosdev.movies.remote.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.talosdev.movies.constants.TMDB;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Custom GsonBuilder that uses the date format defined in {@link TMDB}
 * and is also capable of handling empty dates.
 * Created by apapad on 25/01/16.
 */
public class MovieGsonBuilder {

    private final GsonBuilder builder;

    public MovieGsonBuilder() {
        builder = new GsonBuilder();
    }

    public Gson create() {
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            DateFormat df = new SimpleDateFormat(TMDB.DATE_FORMAT);

            @Override
            public Date deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
                    throws JsonParseException {
                String jsonValue = json.getAsString();
                if (jsonValue == null || jsonValue.equals("")) {
                    return null;
                }
                try {
                    return df.parse(jsonValue);
                } catch (ParseException e) {
                    return null;
                }
            }
        });
        return builder.create();
    }
}
