package app.we.go.movies.remote.json;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by apapad on 2/03/16.
 */
public class Video implements Parcelable {

    public Video() {
    }

    public String id;

    public String key;

    public String site;

    public String name;

    public String type;


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
}
