package com.udacity.nanodegree.popularmovies.ui.activities.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.udacity.nanodegree.popularmovies.data.MovieDTO;
import com.udacity.nanodegree.popularmovies.data.ReviewDTO;
import com.udacity.nanodegree.popularmovies.data.ReviewResultDTO;
import com.udacity.nanodegree.popularmovies.data.TrailerDTO;
import com.udacity.nanodegree.popularmovies.data.VideosResultDTO;
import com.udacity.nanodegree.popularmovies.services.MoviesService;
import com.udacity.nanodegree.popularmovies.ui.activities.MovieDetailActivity;
import com.udacity.nanodegree.popularmovies.ui.activities.adapters.ReviewsAdapter;
import com.udacity.nanodegree.popularmovies.ui.activities.adapters.TrailerAdapter;
import com.udacity.nanodegree.popularmovies.utils.RetrofitUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailPresenter {

    private final MoviesService moviesService;
    private TrailerAdapter trailerAdapter;
    private ReviewsAdapter reviewAdapter;
    private final MovieDetailActivity activity;
    private final MovieDTO movie;
    private int reviewsPage = 1;

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
            trailerAdapter = new TrailerAdapter();
            activity.setTrailersRecyclerAdapter(trailerAdapter);
            trailerAdapter.setItems(items);
            return;
        }

        moviesService.getVideos(String.valueOf(movie.getId())).enqueue(new Callback<VideosResultDTO>() {
            @Override
            public void onResponse(@NonNull Call<VideosResultDTO> call, @NonNull Response<VideosResultDTO> response) {
                activity.loading = false;
                activity.toggleLoading();
                if (response.body() == null)
                    return;
                trailerAdapter = new TrailerAdapter(response.body().getVideos());
                activity.setTrailersRecyclerAdapter(trailerAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<VideosResultDTO> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void loadReviewsAdapter(@Nullable List<ReviewDTO> items) {
        activity.loading = true;
        activity.toggleLoading();

        if (items != null) {
            activity.loading = false;
            activity.toggleLoading();
            reviewAdapter = new ReviewsAdapter();
            activity.setReviewsRecyclerAdapter(reviewAdapter);
            reviewAdapter.setItems(items);
            return;
        }

        moviesService.getReviews(String.valueOf(movie.getId()), 1).enqueue(new Callback<ReviewResultDTO>() {
            @Override
            public void onResponse(@NonNull Call<ReviewResultDTO> call, @NonNull Response<ReviewResultDTO> response) {
                activity.loading = false;
                activity.toggleLoading();
                if (response.body() == null)
                    return;

                activity.setReviewsRecyclerAdapter(new ReviewsAdapter(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<ReviewResultDTO> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void nextReviewsPage() {
        if (reviewAdapter == null) {
            return;
        }

        moviesService.getReviews(String.valueOf(movie.getId()), ++reviewsPage).enqueue(new Callback<ReviewResultDTO>() {
            @Override
            public void onResponse(@NonNull Call<ReviewResultDTO> call, @NonNull Response<ReviewResultDTO> response) {
                activity.loading = false;
                activity.toggleLoading();
                if (response.body() == null)
                    return;

                reviewAdapter.addItems(response.body().getResults());
            }

            @Override
            public void onFailure(@NonNull Call<ReviewResultDTO> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void setReviewPage(int page) {
        reviewsPage = page;
    }
}
