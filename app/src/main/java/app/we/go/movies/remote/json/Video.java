package app.we.go.movies.remote.json;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by apapad on 2/03/16.
 */
public class Video implements Parcelable {

    public Video() {
    }

    @NonNull
    private String id;

    private String key;

    private String site;

    private String name;

    private String type;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.key);
        dest.writeString(this.site);
        dest.writeString(this.name);
        dest.writeString(this.type);
    }

    private Video(Parcel in) {
        this.id = in.readString();
        this.key = in.readString();
        this.site = in.readString();
        this.name = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<Video> CREATOR = new Parcelable.Creator<Video>() {
        public Video createFromParcel(Parcel source) {
            return new Video(source);
        }

        public Video[] newArray(int size) {
            return new Video[size];
        }
    };


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Video)) return false;

        Video video = (Video) o;

        if (!getId().equals(video.getId())) return false;
        if (getKey() != null ? !getKey().equals(video.getKey()) : video.getKey() != null)
            return false;
        if (getSite() != null ? !getSite().equals(video.getSite()) : video.getSite() != null)
            return false;
        if (getName() != null ? !getName().equals(video.getName()) : video.getName() != null)
            return false;
        return getType() != null ? getType().equals(video.getType()) : video.getType() == null;

    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + (getKey() != null ? getKey().hashCode() : 0);
        result = 31 * result + (getSite() != null ? getSite().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        return result;
    }
}
