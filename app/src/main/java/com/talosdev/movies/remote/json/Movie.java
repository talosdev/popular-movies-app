package com.talosdev.movies.remote.json;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Java object to which the json response with details about a movie is deserialized.
 * Created by apapad on 13/11/15.
 */
public class Movie {

    public Movie() {
    }

    @SerializedName("id")
    public long id;

    @SerializedName("original_title")
    public String title;

    @SerializedName("overview")
    public String overview;

    @SerializedName("release_date")
    public Date releaseDate;

    @SerializedName("poster_path")
    public String posterPath;

    @SerializedName("backdrop_path")
    public String backdropPath;

    @SerializedName("popularity")
    public float popularity;

    @SerializedName("vote_average")
    public float voteAverage;

    @SerializedName("vote_count")
    public long voteCount;


//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeLong(id);
//        dest.writeString(title);
//        dest.writeString(overview);
//        dest.writeLong(releaseDate.getTime());
//        dest.writeString(posterPath);
//        dest.writeString(backdropPath);
//        dest.writeFloat(popularity);
//        dest.writeFloat(voteAverage);
//        dest.writeLong(voteCount);
//    }
//
//    public static final Parcelable.Creator<Movie> CREATOR
//            = new Parcelable.Creator<Movie>() {
//        public Movie createFromParcel(Parcel in) {
//            return new Movie(in);
//        }
//
//        public Movie[] newArray(int size) {
//            return new Movie[size];
//        }
//    };
//
//    private Movie(Parcel in) {
//        id = in.readInt();
//        title = in.readString();
//        overview = in.readString();
//        releaseDate = new Date(in.readLong());
//        posterPath = in.readString();
//        backdropPath= in.readString();
//        popularity = in.readFloat();
//        voteAverage = in.readFloat();
//        voteCount = in.readLong();
//    }


}
