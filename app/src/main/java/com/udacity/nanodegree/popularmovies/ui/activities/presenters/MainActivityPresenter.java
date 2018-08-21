package com.udacity.nanodegree.popularmovies.ui.activities.presenters;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import com.udacity.nanodegree.popularmovies.R;
import com.udacity.nanodegree.popularmovies.services.MoviesService;
import com.udacity.nanodegree.popularmovies.data.ResultDTO;
import com.udacity.nanodegree.popularmovies.ui.activities.MainActivity;
import com.udacity.nanodegree.popularmovies.ui.activities.adapters.MoviesAdapter;
import com.udacity.nanodegree.popularmovies.utils.LayoutUtils;
import com.udacity.nanodegree.popularmovies.utils.RetrofitUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter {

    private final MainActivity mainActivity;
    private final MoviesService moviesService;

    public MainActivityPresenter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.moviesService = RetrofitUtils.getMoviesService();
    }

    public RecyclerView.LayoutManager getRecyclerLayoutManager() {
        return new GridLayoutManager(mainActivity, LayoutUtils.getSpanCount(mainActivity),
                LinearLayoutManager.VERTICAL, false);
    }


    public void getMoviesAdapter() {
        moviesService.getMovies(mainActivity.getString(R.string.query_popular)).enqueue(new Callback<ResultDTO>() {
            @Override
            public void onResponse(@NonNull Call<ResultDTO> call, @NonNull Response<ResultDTO> response) {
                mainActivity.setMoviesRecyclerAdapter(new MoviesAdapter(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<ResultDTO> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void filterMovies(String string) {
        moviesService.getMovies(string).enqueue(new Callback<ResultDTO>() {
            @Override
            public void onResponse(@NonNull Call<ResultDTO> call, @NonNull Response<ResultDTO> response) {
                mainActivity.adapter.filterMovies(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ResultDTO> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
