package com.udacity.nanodegree.popularmovies.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResultDTO {

    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("page")
    private int page;

    @Expose
    @SerializedName("total_pages")
    private int totalPages;

    @Expose
    @SerializedName("total_results")
    private int totalResults;

    @Expose
    @SerializedName("results")
    private List<ReviewDTO> results;

    public ReviewResultDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<ReviewDTO> getResults() {
        return results;
    }

    public void setResults(List<ReviewDTO> results) {
        this.results = results;
    }
}
