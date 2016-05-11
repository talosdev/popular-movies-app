package app.we.go.movies.movielist;

import app.we.go.movies.common.BasePresenter;
import app.we.go.movies.common.BaseView;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public interface MovieListContract {


    interface View extends BaseView {

        void showMovieDetails(String movieId);

    }

    interface Presenter extends BasePresenter<View> {

        void openMovieDetails(String movieId);
    }
}
