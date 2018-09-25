package com.udacity.nanodegree.popularmovies.ui.activities.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.udacity.nanodegree.popularmovies.data.MovieDTO;
import com.udacity.nanodegree.popularmovies.data.TrailerDTO;
import com.udacity.nanodegree.popularmovies.data.VideosResultDTO;
import com.udacity.nanodegree.popularmovies.services.MoviesService;
import com.udacity.nanodegree.popularmovies.ui.activities.MovieDetailActivity;
import com.udacity.nanodegree.popularmovies.ui.activities.adapters.TrailerAdapter;
import com.udacity.nanodegree.popularmovies.utils.RetrofitUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailPresenter {

    private final MoviesService moviesService;
    private TrailerAdapter adapter;
    private final MovieDetailActivity activity;
    private final MovieDTO movie;

    public MovieDetailPresenter(MovieDetailActivity movieDetailActivity, MovieDTO movie) {
        this.activity = movieDetailActivity;
        this.moviesService = RetrofitUtils.getMoviesService();
        this.movie = movie;
    }

    public RecyclerView.LayoutManager getRecyclerLayoutManager() {
        return new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
    }

    public void loadTrailersAdapter(@Nullable List<TrailerDTO> items) {
        activity.loading = true;
        activity.toggleLoading();

        if (items != null) {
            activity.loading = false;
            activity.toggleLoading();
            activity.setTrailersRecyclerAdapter(new TrailerAdapter());
            adapter.setItems(items);
            return;
        }

        moviesService.getVideos(String.valueOf(movie.getId())).enqueue(new Callback<VideosResultDTO>() {
            @Override
            public void onResponse(@NonNull Call<VideosResultDTO> call, @NonNull Response<VideosResultDTO> response) {
                activity.loading = false;
                activity.toggleLoading();
                if (response.body() == null)
                    return;
                activity.setTrailersRecyclerAdapter(new TrailerAdapter(response.body().getVideos()));
            }

            @Override
            public void onFailure(@NonNull Call<VideosResultDTO> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
