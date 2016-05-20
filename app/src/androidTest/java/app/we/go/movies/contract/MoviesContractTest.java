package app.we.go.movies.contract;

import android.net.Uri;

import app.we.go.movies.contract.MoviesContract.FavoriteMovieEntry;

import static app.we.go.movies.contract.MoviesContract.FavoriteMovieEntry.getMovieIdFromUri;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * TODO remove this
 * Created by apapad on 2016-01-15.
 */
public class MoviesContractTest {


    public static final String FAVORITE_URI = "content://app.we.go.movies/favorite/1000";


 //   @Test
    public void testBuildFavoriteMovieUri() {
        assertThat(FavoriteMovieEntry.buildFavoriteMovieUri(1000l)).
                isEqualTo(Uri.parse(FAVORITE_URI));
    }

 //   @Test
    public void testGetMovieIdFromUri() {
        assertThat(getMovieIdFromUri(Uri.parse(FAVORITE_URI))).
                isEqualTo(1000l);
    }
//
//    @Test
//    public void testBuildMovieIdWithPosterPath() {
//        assertThat(FavoriteMovieEntry.
//                buildFavoriteMovieUriWithPosterPath(1000l, POSTER_PATH).toString()).
//                isEqualTo(FAVORITE_URI_WITH_POSTER);
//    }


}