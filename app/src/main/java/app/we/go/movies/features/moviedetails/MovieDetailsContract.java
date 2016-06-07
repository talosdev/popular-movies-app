package app.we.go.movies.features.moviedetails;

import android.net.Uri;

import java.util.List;

import app.we.go.framework.mvp.presenter.CacheablePresenter;
import app.we.go.framework.mvp.view.ViewMVP;
import app.we.go.movies.model.remote.Movie;
import app.we.go.movies.model.remote.Review;
import app.we.go.movies.model.remote.Video;

/**
 * The MVP contract for the movie details feature.
 * Consists of 3 presenters and 4 views (one presenter is bound to 2 views).
 *
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public interface MovieDetailsContract {


    interface DetailsView extends ViewMVP {

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
     * This presenter is supposed to be bound to both a {@link DetailsView} and a
     * {@link MovieDetailsContract.InfoView}
     */
    interface DetailsPresenter extends CacheablePresenter<DetailsView> {

        void loadMovieInfo();

        void checkFavorite(long movieId);


        void onFavoriteClick(long movieId, String posterPath);
    }

    interface MovieInfoPresenter extends CacheablePresenter<InfoView> {

        void loadMovieInfo();
    }


    interface ReviewsPresenter extends CacheablePresenter<ReviewsView> {
        void loadMovieReviews(long movieId);


    }

    interface VideosPresenter extends CacheablePresenter<VideosView> {
        void loadMovieVideos(long movieId);

        void onVideoClicked(String videoKey);

        void onShareVideoClicked(String videoKey, String videoName);
    }
}
