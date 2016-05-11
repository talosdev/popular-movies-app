package app.we.go.movies.moviedetails;

import java.util.List;

import app.we.go.movies.R;
import app.we.go.movies.common.AbstractPresenter;
import app.we.go.movies.constants.Tags;
import app.we.go.movies.remote.TMDBService;
import app.we.go.movies.remote.json.Review;
import app.we.go.movies.remote.json.ReviewList;
import app.we.go.movies.util.LOG;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class MovieReviewsPresenter extends AbstractPresenter<MovieDetailsContract.ReviewsView> implements MovieDetailsContract.ReviewsPresenter {


    private final TMDBService service;

    private List<Review> reviews;


    public MovieReviewsPresenter(TMDBService service) {
        this.service = service;
    }

    @Override
    public void loadMovieReviews(final long movieId) {
        Call<ReviewList> call = service.getReviews(movieId);
        call.enqueue(new Callback<ReviewList>() {
            @Override
            public void onResponse(Call<ReviewList> call, Response<ReviewList> response) {
                if (response.isSuccess()) {
                    if (getBoundView() != null) {
                        reviews = response.body().reviews;
                        getBoundView().displayReviews(reviews);
                    }
                } else {
                    LOG.e(Tags.REMOTE, "Movies response was not successful for %d", movieId);
                    onError();
                }

            }

            @Override
            public void onFailure(Call<ReviewList> call, Throwable t) {
                LOG.e(Tags.REMOTE, t, "Call for getting reviews for %d failed", movieId);
                onError();
            }

        });
    }

    private void onError() {
        if (getBoundView() != null) {
            getBoundView().displayError(R.string.error_network);
        }
    }
}
