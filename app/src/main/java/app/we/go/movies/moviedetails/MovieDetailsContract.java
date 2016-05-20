package app.we.go.movies.moviedetails;

import android.net.Uri;
import android.support.annotation.StringRes;

import java.util.List;

import app.we.go.movies.common.BasePresenter;
import app.we.go.movies.common.BaseView;
import app.we.go.movies.remote.json.Movie;
import app.we.go.movies.remote.json.Review;
import app.we.go.movies.remote.json.Video;

/**
 * The MVP contract for the movie details feature.
 * Consists of 3 presenters and 4 views (one presenter is bound to 2 views).
 *
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public interface MovieDetailsContract {


    interface View extends BaseView {

        void toggleFavorite(boolean isFavorite);

        void displayError(@StringRes int errorResource);

        void displayTitle(String title);

        void displayImage(String imagePath);
    }

    interface InfoView extends BaseView {
        void displayInfo(Movie movie);
        void displayFormattedDate(String date);
    }

    interface ReviewsView extends BaseView {
        void displayReviews(List<Review> body);

        void displayError(@StringRes int errorMessage);
    }

    interface VideosView extends BaseView {
        void displayVideos(List<Video> videos);

        void displayError(@StringRes int errorMessage);

        void openVideo(Uri videoUrl);

        void shareVideo(Uri videoUrl, String videoName);
    }

    /**
     * This presenter is supposed to be bound to both a {@link MovieDetailsContract.View} and a
     * {@link MovieDetailsContract.InfoView}
     */
    interface Presenter extends BasePresenter<View> {

        void bindInfoView(InfoView infoView);

        void unbindInfoView();

        void unbindAllViews();

        InfoView getInfoView();


        void checkFavorite(long movieId);


        void loadMovieInfo(long movieId);


        void onFavoriteClick(long movieId);
    }


    interface ReviewsPresenter extends BasePresenter<ReviewsView> {
        void loadMovieReviews(long movieId);
    }

    interface VideosPresenter extends BasePresenter<VideosView> {
        void loadMovieVideos(long movieId);

        void onVideoClicked(String videoKey);

        void onShareVideoClicked(String videoKey, String videoName);
    }
}
