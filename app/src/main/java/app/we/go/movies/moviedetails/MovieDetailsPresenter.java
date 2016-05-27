package app.we.go.movies.moviedetails;

import app.we.go.movies.SharedPreferencesHelper;
import app.we.go.movies.common.AbstractPresenter;
import app.we.go.movies.db.FavoriteMovieDAO;
import app.we.go.movies.model.FavoriteMovie;
import app.we.go.movies.remote.TMDBService;

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
    }


    @Override
    public void unbindInfoView() {
        this.infoView = null;
    }


    @Override
    public void unbindAllViews() {
        unbindView();
        unbindInfoView();
    }

    @Override
    public MovieDetailsContract.InfoView getInfoView() {
        return infoView;
    }


    @Override
    public void loadMovieInfo(long movieId) {
//        Call<Movie> call = service.getDetails(movieId);
//       // EspressoIdlingResource.increment(); // App is busy until further notice
//
//        call.enqueue(new Callback<Movie>() {
//            @Override
//            public void onResponse(Call<Movie> call, Response<Movie> response) {
//                if (response.isSuccess()) {
//                    Movie movie = response.body();
//                    if (getInfoView() != null) {
//                        getInfoView().displayInfo(movie);
//
//                        getInfoView().displayFormattedDate(sharedPrefsHelper.formatDate(movie.getReleaseDate()));
//
//                    }
//                    if (getBoundView() != null) {
//                        getBoundView().displayTitle(movie.getTitle());
//                        getBoundView().displayImage(movie.getBackdropPath());
//                    }
//                } else {
//                    onError("The call to get the movie details was not successful",
//                            R.string.error_network);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Movie> call, Throwable t) {
//                onFail("Network error getting the movie details",
//                        R.string.error_network,
//                        t);
//            }
//        });

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
