package app.we.go.movies.moviedetails;

import app.we.go.movies.R;
import app.we.go.movies.SharedPreferencesHelper;
import app.we.go.movies.common.AbstractPresenter;
import app.we.go.movies.db.FavoriteMovieDAO;
import app.we.go.movies.model.FavoriteMovie;
import app.we.go.movies.remote.TMDBService;
import app.we.go.movies.remote.json.Movie;
import app.we.go.movies.util.RxUtils;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class MovieDetailsPresenter extends AbstractPresenter<MovieDetailsContract.View> implements MovieDetailsContract.Presenter {

    private TMDBService service;
    private SharedPreferencesHelper sharedPrefsHelper;
    private FavoriteMovieDAO favoriteMovieDAO;

    // Presenter holds the "favorite" state
    private boolean isFavorite;

    MovieDetailsContract.InfoView infoView;

    private Subscription subscription;


    public MovieDetailsPresenter(TMDBService service,
                                 SharedPreferencesHelper sharedPrefsHelper,
                                 FavoriteMovieDAO favoriteMovieDAO) {
        this.service = service;
        this.sharedPrefsHelper = sharedPrefsHelper;
        this.favoriteMovieDAO = favoriteMovieDAO;
    }

    @Override
    public void bindInfoView(MovieDetailsContract.InfoView infoView) {
        this.infoView = infoView;
        RxUtils.unsubscribe(subscription);
    }


    @Override
    public void unbindInfoView() {
        this.infoView = null;
        RxUtils.unsubscribe(subscription);
    }


    @Override
    public void unbindAllViews() {
        unbindView();
        unbindInfoView();
        RxUtils.unsubscribe(subscription);
    }

    @Override
    public MovieDetailsContract.InfoView getInfoView() {
        return infoView;
    }


    @Override
    public void loadMovieInfo(long movieId) {
        Observable<Movie> details = service.getDetails(movieId);

        subscription = details.
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(
                new Observer<Movie>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable t) {
                        onFail("Network error getting the movie details",
                                R.string.error_network,
                                t);
                    }

                    @Override
                    public void onNext(Movie movie) {
                        if (movie != null) {
                            if (getInfoView() != null) {
                                getInfoView().displayInfo(movie);

                                getInfoView().displayFormattedDate(sharedPrefsHelper.formatDate(movie.getReleaseDate()));

                            }
                            if (getBoundView() != null) {
                                getBoundView().displayTitle(movie.getTitle());
                                getBoundView().displayImage(movie.getBackdropPath());
                            }
                        }
                    }
                }

        );


    }


    @Override
    public void checkFavorite(long movieId) {
        boolean isFavorite = favoriteMovieDAO.get(movieId);
        this.isFavorite = isFavorite;
        if (getBoundView() != null) {
            getBoundView().toggleFavorite(isFavorite);
        }
    }


    @Override
    public void onFavoriteClick(long movieId, String posterPath) {
        if (isFavorite) {
            favoriteMovieDAO.delete(movieId);
        } else {
            favoriteMovieDAO.put(new FavoriteMovie(movieId, posterPath));
        }

        isFavorite = !isFavorite;

        // reflect the change in the ui
        if (getBoundView() != null) {
            getBoundView().toggleFavorite(isFavorite);
        }

    }


}
