package app.we.go.movies.remote;

import java.io.IOException;

import app.we.go.movies.constants.TMDB;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by apapad on 10/03/16.
 */
public class TMDBApiKeyInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        HttpUrl newUrl = originalRequest
                .url()
                .newBuilder()
                .addQueryParameter(TMDB.PARAM_API_KEY, TMDB.API_KEY)
                .build();

        Request newRequest = originalRequest.newBuilder()
                .url(newUrl)
                .build();

        return chain.proceed(newRequest);
    }
}
