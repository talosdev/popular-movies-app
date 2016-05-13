package app.we.go.movies.remote.json;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class TMDBError {
    @SerializedName("status_code")
    private int statusCode;

    @SerializedName("status_message")
    private String statusMessage;

    public TMDBError() {
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
