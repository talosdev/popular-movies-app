package app.we.go.movies.moviedetails;

import java.util.List;

import app.we.go.movies.R;
import app.we.go.movies.common.AbstractPresenter;
import app.we.go.movies.moviedetails.MovieDetailsContract.VideosView;
import app.we.go.movies.remote.TMDBService;
import app.we.go.movies.remote.URLBuilder;
import app.we.go.movies.remote.json.TMDBError;
import app.we.go.movies.remote.json.Video;
import app.we.go.movies.remote.json.VideoList;
import app.we.go.movies.util.RxUtils;
import retrofit2.Response;
import rx.Observer;
import rx.Subscription;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class MovieVideosPresenter extends AbstractPresenter<VideosView> implements MovieDetailsContract.VideosPresenter {


    private final TMDBService service;
    private URLBuilder urlBuilder;

    private List<Video> videos;
    private Subscription subscription;


    public MovieVideosPresenter(TMDBService service,
                                URLBuilder urlBuilder) {
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
                                if (getBoundView() != null) {
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
                        }
                );


    }

    @Override
    public void onVideoClicked(String videoKey) {
        if (getBoundView() != null) {
            getBoundView().openVideo(urlBuilder.buildYoutubeUri(videoKey));
        }
    }

    @Override
    public void onShareVideoClicked(String videoKey, String videoName) {
        if (getBoundView() != null) {
            getBoundView().shareVideo(urlBuilder.buildYoutubeUri(videoKey), videoName);
        }
    }

}
