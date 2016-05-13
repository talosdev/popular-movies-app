package app.we.go.movies.remote.json;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Java object to which the json response with details about a movie is deserialized.
 * Created by apapad on 13/11/15.
 */
public class Movie implements Parcelable {

    public static final String BUNDLE_KEY = "app.we.go.movies.BUNDLE.MOVIE";

    public Movie() {
    }

    @SerializedName("id")
    private long id;

    @SerializedName("original_title")
    private String title;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private Date releaseDate;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("popularity")
    public float popularity;

    @SerializedName("vote_average")
    public float voteAverage;

    @SerializedName("vote_count")
    public long voteCount;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(long voteCount) {
        this.voteCount = voteCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeLong(releaseDate.getTime());
        dest.writeString(posterPath);
        dest.writeString(backdropPath);
        dest.writeFloat(popularity);
        dest.writeFloat(voteAverage);
        dest.writeLong(voteCount);
    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    private Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        overview = in.readString();
        releaseDate = new Date(in.readLong());
        posterPath = in.readString();
        backdropPath= in.readString();
        popularity = in.readFloat();
        voteAverage = in.readFloat();
        voteCount = in.readLong();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;

        Movie movie = (Movie) o;

        if (getId() != movie.getId()) return false;
        if (Float.compare(movie.getPopularity(), getPopularity()) != 0) return false;
        if (Float.compare(movie.getVoteAverage(), getVoteAverage()) != 0) return false;
        if (getVoteCount() != movie.getVoteCount()) return false;
        if (getTitle() != null ? !getTitle().equals(movie.getTitle()) : movie.getTitle() != null)
            return false;
        if (getOverview() != null ? !getOverview().equals(movie.getOverview()) : movie.getOverview() != null)
            return false;
        if (getReleaseDate() != null ? !getReleaseDate().equals(movie.getReleaseDate()) : movie.getReleaseDate() != null)
            return false;
        if (getPosterPath() != null ? !getPosterPath().equals(movie.getPosterPath()) : movie.getPosterPath() != null)
            return false;
        return getBackdropPath() != null ? getBackdropPath().equals(movie.getBackdropPath()) : movie.getBackdropPath() == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getOverview() != null ? getOverview().hashCode() : 0);
        result = 31 * result + (getReleaseDate() != null ? getReleaseDate().hashCode() : 0);
        result = 31 * result + (getPosterPath() != null ? getPosterPath().hashCode() : 0);
        result = 31 * result + (getBackdropPath() != null ? getBackdropPath().hashCode() : 0);
        result = 31 * result + (getPopularity() != +0.0f ? Float.floatToIntBits(getPopularity()) : 0);
        result = 31 * result + (getVoteAverage() != +0.0f ? Float.floatToIntBits(getVoteAverage()) : 0);
        result = 31 * result + (int) (getVoteCount() ^ (getVoteCount() >>> 32));
        return result;
    }
}
