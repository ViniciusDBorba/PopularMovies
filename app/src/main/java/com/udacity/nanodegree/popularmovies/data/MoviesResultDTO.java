package com.udacity.nanodegree.popularmovies.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MoviesResultDTO {

    @Expose
    @SerializedName("page")
    private int page;

    @Expose
    @SerializedName("total_results")
    private int totalResults;

    @Expose
    @SerializedName("total_pages")
    private int totalPages;

    @Expose
    @SerializedName("results")
    private List<MovieDTO> results = new ArrayList<>();

    public MoviesResultDTO() {

    }

    public MoviesResultDTO(int page, int totalResults, int totalPages, List<MovieDTO> results) {
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
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

    public List<MovieDTO> getResults() {
        return results;
    }

    public void setResults(List<MovieDTO> results) {
        this.results = results;
    }
}
