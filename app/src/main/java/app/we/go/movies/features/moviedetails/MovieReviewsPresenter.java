package app.we.go.movies.features.moviedetails;

import android.support.annotation.NonNull;

import java.util.List;

import app.we.go.framework.mvp.presenter.PresenterFactory;
import app.we.go.movies.R;
import app.we.go.framework.mvp.presenter.BaseCacheablePresenter;
import app.we.go.framework.mvp.presenter.PresenterCache;
import app.we.go.movies.remote.service.TMDBService;
import app.we.go.movies.model.remote.Review;
import app.we.go.movies.model.remote.ReviewList;
import app.we.go.movies.model.remote.TMDBError;
import app.we.go.framework.util.RxUtils;
import retrofit2.Response;
import rx.Observer;
import rx.Subscription;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class MovieReviewsPresenter extends BaseCacheablePresenter<MovieDetailsContract.ReviewsView> implements MovieDetailsContract.ReviewsPresenter {


    private final TMDBService service;

    private List<Review> reviews;
    private Subscription subscription;


    public MovieReviewsPresenter(TMDBService service,
                                 PresenterCache cache, String presenterTag) {
        super(cache, presenterTag);
        this.service = service;
    }

    @Override
    public void loadMovieReviews(final long movieId) {
        subscription = service.getReviews(movieId).
                subscribe(
                        new Observer<Response<ReviewList>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable t) {
                                onCallFail("Network error in call to get reviews list",
                                        R.string.error_generic,
                                        t);
                            }

                            @Override
                            public void onNext(Response<ReviewList> response) {
                                if (response.isSuccessful()) {
                                    reviews = response.body().getReviews();
                                    getBoundView().displayReviews(reviews);
                                } else {
                                    // If we want to access the error:
                                    TMDBError error = service.parse(response.errorBody());


                                    onCallError("The call to get the reviews list was unsuccessful",
                                            R.string.error_generic,
                                            error);
                                }
                            }
                        }
                );
    }

    @Override
    public void onRestoreFromCache() {
        if (isViewBound()) {
            getBoundView().displayReviews(reviews);
        }
    }

    @Override
    public void unbindView() {
        super.unbindView();
        RxUtils.unsubscribe(subscription);
    }



    public static class Factory implements PresenterFactory<MovieReviewsPresenter> {
        private PresenterCache cache;
        private TMDBService service;

        public Factory(PresenterCache cache, TMDBService service) {
            this.cache = cache;
            this.service = service;
        }


        @NonNull
        @Override
        public MovieReviewsPresenter createPresenter(String tag) {
            return new MovieReviewsPresenter(service,
                    cache,
                    tag);
        }
    }
}
