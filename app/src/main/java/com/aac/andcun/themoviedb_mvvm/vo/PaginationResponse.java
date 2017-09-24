package com.aac.andcun.themoviedb_mvvm.vo;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuneytcarikci on 23/05/2017.
 */

public class PaginationResponse<T extends Id> {

    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("results")
    @Expose
    private List<T> results = null;
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

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
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
        List<Integer> ids = new ArrayList<>();
        for (Id id : results) {
            ids.add(id.getId());
        }
        return ids;
    }

    public Integer getNextPage() {
        if (page < totalPages)
            return page + 1;
        return null;
    }
}
