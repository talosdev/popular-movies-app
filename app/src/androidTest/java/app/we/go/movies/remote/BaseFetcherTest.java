package app.we.go.movies.remote;

import org.junit.BeforeClass;

import app.we.go.movies.dependency.ApplicationModule;

/**
 * Created by apapad on 11/03/16.
 */
public class BaseFetcherTest {
    protected static TMDBService service;


    @BeforeClass
    public static void initService() {
        ApplicationModule module = new ApplicationModule();
        service = module.provideTMDBService(module.provideGson(), new TMDBApiKeyInterceptor());
    }
}

