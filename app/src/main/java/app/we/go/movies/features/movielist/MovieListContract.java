package app.we.go.movies.features.movielist;

import java.util.List;

import app.we.go.framework.mvp.presenter.CacheablePresenter;
import app.we.go.framework.mvp.view.ViewMVP;
import app.we.go.movies.model.remote.Movie;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public interface MovieListContract {


    interface View extends ViewMVP {

        void showMovieList(List<Movie> movies);
        void navigateToMovieDetails(Movie movie);
    }
    interface Presenter extends CacheablePresenter<View> {

        void loadMovies();
        void openMovieDetails(Movie movie);
    }
}
