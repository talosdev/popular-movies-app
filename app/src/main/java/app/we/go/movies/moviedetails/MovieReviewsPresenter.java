package app.we.go.movies.moviedetails;

import java.util.List;

import app.we.go.movies.R;
import app.we.go.movies.common.AbstractPresenter;
import app.we.go.movies.remote.TMDBService;
import app.we.go.movies.remote.json.Review;
import app.we.go.movies.remote.json.ReviewList;
import app.we.go.movies.remote.json.TMDBError;
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
                        reviews = response.body().getReviews();
                        getBoundView().displayReviews(reviews);
                    }
                } else {
                    // If we want to access the error:
                    TMDBError error = service.parse(response.errorBody());


                    onError("The call to get the reviews list was unsuccessful",
                            R.string.error_network);
                }
            }

            @Override
            public void onFailure(Call<ReviewList> call, Throwable t) {

                onFail("Network error in call to get reviews list",
                        R.string.error_network,
                        t);
            }

        });
    }


}
