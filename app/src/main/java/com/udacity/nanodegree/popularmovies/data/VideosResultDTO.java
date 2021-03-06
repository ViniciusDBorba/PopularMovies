package com.udacity.nanodegree.popularmovies.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VideosResultDTO {

    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("results")
    private List<TrailerDTO> videos = new ArrayList<>();

    public VideosResultDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TrailerDTO> getVideos() {
        return videos;
    }

    public void setVideos(List<TrailerDTO> videos) {
        this.videos = videos;
    }
}
