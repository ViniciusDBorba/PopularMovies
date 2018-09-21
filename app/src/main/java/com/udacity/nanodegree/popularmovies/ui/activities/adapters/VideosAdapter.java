package com.udacity.nanodegree.popularmovies.ui.activities.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.udacity.nanodegree.popularmovies.data.VideoDTO;

import java.util.ArrayList;
import java.util.List;

public class VideosAdapter extends RecyclerView.Adapter<VideosViewHolder> {

    private List<VideoDTO> trailers = new ArrayList<>();

    public VideosAdapter(List<VideoDTO> trailers) {
        this.trailers = trailers;
    }

    public VideosAdapter() {
    }

    @NonNull
    @Override
    public VideosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull VideosViewHolder videosViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public void setItems(List<VideoDTO> items) {
        this.trailers = items;
    }
}
