package com.udacity.nanodegree.popularmovies.ui.activities.presenters;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import com.udacity.nanodegree.popularmovies.ui.activities.adapters.MoviesAdapter;

public class MainActivityPresenter {

    private final Context context;

    public MainActivityPresenter(Context context) {
        this.context = context;
    }

    public RecyclerView.LayoutManager getRecyclerLayoutManager() {
        return new GridLayoutManager(context, getSpanCount(), LinearLayoutManager.VERTICAL, false);
    }

    private int getSpanCount() {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 180);
    }

    public MoviesAdapter getMoviesAdapter() {
        MoviesAdapter adapter = new MoviesAdapter();

        return adapter;
    }
}
