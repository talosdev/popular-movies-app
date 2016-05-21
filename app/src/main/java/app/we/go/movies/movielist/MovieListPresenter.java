package app.we.go.movies.movielist;

import app.we.go.movies.R;
import app.we.go.movies.common.AbstractPresenter;
import app.we.go.movies.data.SortByCriterion;
import app.we.go.movies.remote.TMDBService;
import app.we.go.movies.remote.json.MovieList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class MovieListPresenter extends AbstractPresenter<MovieListContract.View>
        implements MovieListContract.Presenter{

    private TMDBService service;

    private int currentPage = 0;

    public MovieListPresenter(TMDBService service) {
        this.service = service;
    }



    @Override
    public void loadMovies(SortByCriterion sortBy) {
        Call<MovieList> movies = service.getMovies(sortBy, currentPage);
        movies.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (response.isSuccess()) {
                    MovieList movieList = response.body();
                    currentPage++;
                    if (getBoundView() != null) {
                        getBoundView().showMovieList(movieList.getMovies());
                    }
                } else {
                    onError("The get movies list call was not successful",
                            R.string.error_network);
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                onFail("Error receiving movie list from server",
                        R.string.error_network,
                        t);
                }
        });

    }



    @Override
    public void openMovieDetails(long movieId) {
        if (getBoundView()!=null) {
            getBoundView().showMovieDetails(movieId);
        }
    }
}
