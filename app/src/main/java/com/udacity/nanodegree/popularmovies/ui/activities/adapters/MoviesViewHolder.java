package com.udacity.nanodegree.popularmovies.ui.activities.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.udacity.nanodegree.popularmovies.data.MovieDTO;

import butterknife.ButterKnife;

public class MoviesViewHolder extends RecyclerView.ViewHolder {

    public MoviesViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(itemView);
    }

    public void bind(MovieDTO item) {

    }
}
