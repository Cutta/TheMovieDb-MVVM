package com.aac.andcun.themoviedb_mvvm.vo;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuneytcarikci on 23/05/2017.
 */

public class PaginationResponse {

    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("results")
    @Expose
    private List<Movie> results = null;
    @SerializedName("total_results")
    @Expose
    private int totalResults;
    @SerializedName("total_pages")
    @Expose
    private int totalPages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @NonNull
    public List<Integer> getMovieIds() {
        List<Integer> movieIds = new ArrayList<>();
        for (Movie movie : results) {
            movieIds.add(movie.getId());
        }
        return movieIds;
    }

    public Integer getNextPage() {
        if (page < totalPages)
            return page + 1;
        return null;
    }
}
