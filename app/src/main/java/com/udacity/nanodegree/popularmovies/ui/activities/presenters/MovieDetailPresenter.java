package com.udacity.nanodegree.popularmovies.ui.activities.presenters;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.udacity.nanodegree.popularmovies.ui.activities.MovieDetailActivity;
import com.udacity.nanodegree.popularmovies.utils.LayoutUtils;

public class MovieDetailPresenter {

    private final MovieDetailActivity activity;

    public MovieDetailPresenter(MovieDetailActivity movieDetailActivity) {
        this.activity = movieDetailActivity;
    }

    public RecyclerView.LayoutManager getRecyclerLayoutManager() {
        return new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
    }


}
