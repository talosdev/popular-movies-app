package app.we.go.movies.features.moviedetails;

import java.util.List;

import app.we.go.movies.R;
import app.we.go.movies.features.moviedetails.MovieDetailsContract.VideosView;
import app.we.go.movies.mvp.AbstractPresenter;
import app.we.go.movies.mvp.PresenterCache;
import app.we.go.movies.remote.service.TMDBService;
import app.we.go.movies.remote.URLBuilder;
import app.we.go.movies.model.remote.TMDBError;
import app.we.go.movies.model.remote.Video;
import app.we.go.movies.model.remote.VideoList;
import app.we.go.movies.util.RxUtils;
import retrofit2.Response;
import rx.Observer;
import rx.Subscription;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class MovieVideosPresenter extends AbstractPresenter<VideosView> implements MovieDetailsContract.VideosPresenter {


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
    public void unbindView() {
        super.unbindView();
        RxUtils.unsubscribe(subscription);
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

}
