package app.we.go.movies.movielist;

import java.util.List;

import app.we.go.movies.common.BasePresenter;
import app.we.go.movies.common.BaseView;
import app.we.go.movies.data.SortByCriterion;
import app.we.go.movies.remote.json.Movie;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public interface MovieListContract {


    interface View extends BaseView {

        void showMovieList(List<Movie> movies);
        void showMovieDetails(Movie movie);
    }

    interface Presenter extends BasePresenter<View> {

        void loadMovies(SortByCriterion sortBy);
        void openMovieDetails(Movie movie);
    }
}
