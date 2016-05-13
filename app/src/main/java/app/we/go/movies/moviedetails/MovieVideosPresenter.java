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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class MovieVideosPresenter extends AbstractPresenter<VideosView> implements MovieDetailsContract.VideosPresenter {


    private final TMDBService service;
    private URLBuilder urlBuilder;

    private List<Video> videos;


    public MovieVideosPresenter(TMDBService service,
                                URLBuilder urlBuilder) {
        this.service = service;
        this.urlBuilder = urlBuilder;
    }



    @Override
    public void loadMovieVideos(final long movieId) {
        Call<VideoList> call = service.getVideos(movieId);
        call.enqueue(new Callback<VideoList>() {
            @Override
            public void onResponse(Call<VideoList> call, Response<VideoList> response) {
                if (getBoundView() != null) {
                    if (response.isSuccess()) {
                        videos = response.body().getVideos();
                        getBoundView().displayVideos(videos);
                    } else {
                        TMDBError error = service.parse(response.errorBody());

                        onError();
                    }
                }
            }

            @Override
            public void onFailure(Call<VideoList> call, Throwable t) {
                onError();
            }

        });
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





    private void onError() {
        if (getBoundView() != null) {
            getBoundView().displayError(R.string.error_network);
        }
    }
}
