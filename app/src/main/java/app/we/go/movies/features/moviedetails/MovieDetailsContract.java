package app.we.go.movies.features.moviedetails;

import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.List;

import app.we.go.framework.mvp.presenter.CacheablePresenter;
import app.we.go.framework.mvp.presenter.PresenterCache;
import app.we.go.framework.mvp.presenter.PresenterFactory;
import app.we.go.framework.mvp.view.ViewMVP;
import app.we.go.movies.model.remote.Movie;
import app.we.go.movies.model.remote.Review;
import app.we.go.movies.model.remote.Video;
import app.we.go.movies.remote.service.TMDBService;

/**
 * The MVP contract for the movie details feature.
 * Consists of 3 presenters and 4 views (one presenter is bound to 2 views).
 *
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public interface MovieDetailsContract {


    interface View extends ViewMVP {

        void toggleFavorite(boolean isFavorite);

        void displayTitle(String title);

        void displayImage(String imagePath);
    }

    interface InfoView extends ViewMVP {
        void displayInfo(Movie movie);
        void displayFormattedDate(String date);
    }

    interface ReviewsView extends ViewMVP {
        void displayReviews(List<Review> body);
    }

    interface VideosView extends ViewMVP {
        void displayVideos(List<Video> videos);

        void openVideo(Uri videoUrl);

        void shareVideo(Uri videoUrl, String videoName);
    }

    /**
     * This presenter is supposed to be bound to both a {@link MovieDetailsContract.View} and a
     * {@link MovieDetailsContract.InfoView}
     */
    interface Presenter extends app.we.go.framework.mvp.presenter.Presenter<View> {

        void bindInfoView(InfoView infoView);

        void unbindInfoView();

        void unbindAllViews();

        InfoView getInfoView();


        void checkFavorite(long movieId);


        void loadMovieInfo(long movieId);


        void onFavoriteClick(long movieId, String posterPath);
    }


    interface ReviewsPresenter extends CacheablePresenter<ReviewsView> {
        void loadMovieReviews(long movieId);


    }

    interface VideosPresenter extends app.we.go.framework.mvp.presenter.Presenter<VideosView> {
        void loadMovieVideos(long movieId);

        void onVideoClicked(String videoKey);

        void onShareVideoClicked(String videoKey, String videoName);
    }
}
