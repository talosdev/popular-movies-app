package app.we.go.movies.remote;

import java.io.IOException;
import java.lang.annotation.Annotation;

import app.we.go.movies.constants.Tags;
import app.we.go.movies.remote.json.TMDBError;
import app.we.go.movies.util.LOG;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class TMDBErrorParser {

    Retrofit retrofit;


    Converter<ResponseBody, TMDBError> converter;


    public TMDBErrorParser(Retrofit retrofit) {
        this.retrofit = retrofit;

        converter = retrofit
                .responseBodyConverter(TMDBError.class, new Annotation[0]);
    }

    public TMDBError parse(ResponseBody errorResponseBody) {
        try {
            return converter.convert(errorResponseBody);
        } catch (IOException e) {
            LOG.e(Tags.REMOTE, e, "Could not parse error json %s", errorResponseBody.toString());
            return null;
        }

    }



}
