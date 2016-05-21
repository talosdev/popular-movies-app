package app.we.go.movies.remote.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Java object to which the json response with a list of  movies is deserialized.
 * Created by apapad on 13/11/15.
 */
public class MovieList {

    public MovieList() {
    }

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<Movie> movies;

    @SerializedName("total_pages")
    private long totalPages;

    @SerializedName("total_results")
    private long totalResults;

    public List<Movie> getMovies() {
        return movies;
    }

    public int getPage() {
        return page;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public long getTotalResults() {
        return totalResults;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }
}
