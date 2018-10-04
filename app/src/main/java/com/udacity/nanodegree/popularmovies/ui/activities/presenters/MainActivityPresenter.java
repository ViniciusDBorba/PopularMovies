package com.udacity.nanodegree.popularmovies.ui.activities.presenters;

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.udacity.nanodegree.popularmovies.R;
import com.udacity.nanodegree.popularmovies.data.MovieDTO;
import com.udacity.nanodegree.popularmovies.data.MoviesResultDTO;
import com.udacity.nanodegree.popularmovies.database.viewModel.FavoritesViewModel;
import com.udacity.nanodegree.popularmovies.services.MoviesService;
import com.udacity.nanodegree.popularmovies.ui.activities.MainActivity;
import com.udacity.nanodegree.popularmovies.ui.activities.adapters.FavoritesAdapter;
import com.udacity.nanodegree.popularmovies.ui.activities.adapters.MoviesAdapter;
import com.udacity.nanodegree.popularmovies.utils.RetrofitUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter {

    private final MainActivity mainActivity;
    private final MoviesService moviesService;
    private final FavoritesViewModel viewModel;

    private int page = 1;
    private String query;

    public MainActivityPresenter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.moviesService = RetrofitUtils.getMoviesService();
        this.viewModel = ViewModelProviders.of(mainActivity).get(FavoritesViewModel.class);
    }

    public void loadMoviesAdapter(String query, int page) {
        mainActivity.loading = true;
        mainActivity.toggleLoading();

        loadFromService(query, page);
    }

    public void loadMoviesAdapter(@Nullable List<MovieDTO> items) {
        mainActivity.loading = true;
        mainActivity.toggleLoading();
        if (query == null)
            query = mainActivity.getString(R.string.query_popular);


        if (items != null) {
            mainActivity.loading = false;
            mainActivity.toggleLoading();
            mainActivity.setMoviesRecyclerAdapter(new MoviesAdapter());
            mainActivity.moviesAdapter.setItems(items);
            return;
        }

        loadFromService(query, this.page);
    }

    public void removeFavoritesObservable() {
        viewModel.removeObservable(this.mainActivity);
    }

    public void loadFavoritesAdapter(FavoritesAdapter adapter) {
        mainActivity.loading = true;
        mainActivity.toggleLoading();

        if (adapter == null) {
            mainActivity.loading = false;
            mainActivity.toggleLoading();
            adapter = new FavoritesAdapter(viewModel.getAllFavorites());
            mainActivity.setMoviesRecyclerAdapter(adapter);
        }

        FavoritesAdapter finalAdapter = adapter;
        viewModel.getFavoritesLiveData().observe(mainActivity, favorites -> {
            mainActivity.loading = false;
            mainActivity.toggleLoading();
            finalAdapter.setItems(favorites);
        });

    }

    private void loadFromService(String query, int page) {
        moviesService.getMovies(query, page).enqueue(new Callback<MoviesResultDTO>() {
            @Override
            public void onResponse(@NonNull Call<MoviesResultDTO> call, @NonNull Response<MoviesResultDTO> response) {
                mainActivity.loading = false;
                mainActivity.toggleLoading();
                mainActivity.setMoviesRecyclerAdapter(new MoviesAdapter(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResultDTO> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void filterMovies(String string, final int page) {
        mainActivity.loading = true;
        mainActivity.toggleLoading();
        query = string;
        moviesService.getMovies(string, page).enqueue(new Callback<MoviesResultDTO>() {
            @Override
            public void onResponse(@NonNull Call<MoviesResultDTO> call, @NonNull Response<MoviesResultDTO> response) {
                if (mainActivity.moviesAdapter == null) {
                    mainActivity.setMoviesRecyclerAdapter(new MoviesAdapter());
                }

                if (page <= 1) {
                    mainActivity.moviesAdapter.filterMovies(response.body());
                } else {
                    mainActivity.moviesAdapter.addMovies(response.body());
                }

                mainActivity.loading = false;
                mainActivity.toggleLoading();
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResultDTO> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void nextPage() {
        filterMovies(query, page++);
    }

    public int getPage() {
        return page;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setPage(int page) {
        this.page = page;
    }


}
