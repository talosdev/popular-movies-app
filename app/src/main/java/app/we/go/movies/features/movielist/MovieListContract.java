package app.we.go.movies.features.movielist;

import java.util.List;

import app.we.go.movies.model.local.SortByCriterion;
import app.we.go.movies.mvp.BasePresenter;
import app.we.go.movies.mvp.BaseView;
import app.we.go.movies.model.remote.Movie;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public interface MovieListContract {


    interface View extends BaseView {

        void showMovieList(List<Movie> movies);
        void navigateToMovieDetails(Movie movie);
    }

    interface Presenter extends BasePresenter<View> {

        void loadMovies(SortByCriterion sortBy);
        void openMovieDetails(Movie movie);
    }
}
