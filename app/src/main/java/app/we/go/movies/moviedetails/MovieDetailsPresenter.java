package app.we.go.movies.moviedetails;

import app.we.go.movies.R;
import app.we.go.movies.common.AbstractPresenter;
import app.we.go.movies.remote.TMDBService;
import app.we.go.movies.remote.json.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class MovieDetailsPresenter extends AbstractPresenter<MovieDetailsContract.View> implements MovieDetailsContract.Presenter {

    private TMDBService service;

    MovieDetailsContract.InfoView infoView;


    public MovieDetailsPresenter(TMDBService service) {
        this.service = service;
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
        Call<Movie> call = service.getDetails(movieId);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccess()) {
                    Movie movie = response.body();
                    if (getInfoView() != null) {
                        getInfoView().displayInfo(movie);
                    }
                    if (getBoundView() != null) {
                        getBoundView().displayTitle(movie.title);
                        getBoundView().displayImage(movie.backdropPath);
                    }
                } else {
                    onError();
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                onError();
            }
        });

    }

    private void onError() {
        if (getBoundView() != null) {
            getBoundView().displayError(R.string.error_network);
        }
    }


    @Override
    public void checkFavorite(long movieId) {

    }



    @Override
    public void toggleFavorite(long movieId) {

    }


}
