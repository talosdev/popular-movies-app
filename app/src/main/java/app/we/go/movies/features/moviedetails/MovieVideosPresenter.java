package app.we.go.movies.features.moviedetails;

import android.support.annotation.NonNull;

import java.util.List;

import app.we.go.framework.mvp.presenter.PresenterFactory;
import app.we.go.movies.R;
import app.we.go.movies.features.moviedetails.MovieDetailsContract.VideosView;
import app.we.go.framework.mvp.presenter.BaseCacheablePresenter;
import app.we.go.framework.mvp.presenter.PresenterCache;
import app.we.go.movies.remote.service.TMDBService;
import app.we.go.movies.remote.URLBuilder;
import app.we.go.movies.model.remote.TMDBError;
import app.we.go.movies.model.remote.Video;
import app.we.go.movies.model.remote.VideoList;
import app.we.go.framework.util.RxUtils;
import retrofit2.Response;
import rx.Observer;
import rx.Subscription;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class MovieVideosPresenter extends BaseCacheablePresenter<VideosView> implements MovieDetailsContract.VideosPresenter {


    private final TMDBService service;
    private final URLBuilder urlBuilder;

    private List<Video> videos;
    private Subscription subscription;


    public MovieVideosPresenter(TMDBService service,
                                URLBuilder urlBuilder,
                                PresenterCache cache,
                                String tag) {
        super(cache, tag);
        this.service = service;
        this.urlBuilder = urlBuilder;
    }

    @Override
    public void loadMovieVideos(final long movieId) {
        subscription = service.getVideos(movieId).
                subscribe(
                        new Observer<Response<VideoList>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable t) {
                                onCallFail("Error getting list of videos",
                                        R.string.error_network,
                                        t);
                            }


                            @Override
                            public void onNext(Response<VideoList> response) {
                                    if (response.isSuccessful()) {
                                        videos = response.body().getVideos();
                                        getBoundView().displayVideos(videos);
                                    } else {
                                        TMDBError error = service.parse(response.errorBody());

                                        onCallError("The get videos call was unsuccessfull",
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
            getBoundView().displayVideos(videos);
        }
    }

    @Override
    public void onVideoClicked(String videoKey) {
        if (isViewBound()) {
            getBoundView().openVideo(urlBuilder.buildYoutubeUri(videoKey));
        }
    }

    @Override
    public void onShareVideoClicked(String videoKey, String videoName) {
        if (isViewBound()) {
            getBoundView().shareVideo(urlBuilder.buildYoutubeUri(videoKey), videoName);
        }
    }

    @Override
    public void unbindView() {
        super.unbindView();
        RxUtils.unsubscribe(subscription);
    }


    public static class Factory implements PresenterFactory<MovieVideosPresenter> {

        private TMDBService service;
        private URLBuilder urlBuilder;
        private PresenterCache cache;

        public Factory(TMDBService service,
                       URLBuilder urlBuilder,
                       PresenterCache cache) {

            this.service = service;
            this.urlBuilder = urlBuilder;
            this.cache = cache;
        }

        @NonNull
        @Override
        public MovieVideosPresenter createPresenter(String tag) {
            return new MovieVideosPresenter(service,
                    urlBuilder,
                    cache,
                    tag);
        }
    }
}
