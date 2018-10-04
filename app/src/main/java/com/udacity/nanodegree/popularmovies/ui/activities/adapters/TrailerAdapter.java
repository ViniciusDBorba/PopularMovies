package com.udacity.nanodegree.popularmovies.ui.activities.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.nanodegree.popularmovies.R;
import com.udacity.nanodegree.popularmovies.data.TrailerDTO;

import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerViewHolder> {

    private List<TrailerDTO> trailers = new ArrayList<>();

    public TrailerAdapter(List<TrailerDTO> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    public TrailerAdapter() {
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trailer_list_item, viewGroup, false);
        return new TrailerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder trailerViewHolder, int i) {
        trailerViewHolder.bind(getItem(i));
    }

    public TrailerDTO getItem(int position) {
        return trailers.get(position);
    }

    @Override
    public int getItemCount() {
        if (trailers == null) {
            return 0;
        }
        return trailers.size();
    }

    public void setItems(List<TrailerDTO> items) {
        this.trailers = items;
    }
}
