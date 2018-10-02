package com.udacity.nanodegree.popularmovies.ui.activities.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.udacity.nanodegree.popularmovies.R;
import com.udacity.nanodegree.popularmovies.data.MovieDTO;
import com.udacity.nanodegree.popularmovies.database.AppDatabase;
import com.udacity.nanodegree.popularmovies.database.DAOs.MovieDao;
import com.udacity.nanodegree.popularmovies.database.entities.MovieEntity;
import com.udacity.nanodegree.popularmovies.services.MoviesService;
import com.udacity.nanodegree.popularmovies.data.MoviesResultDTO;
import com.udacity.nanodegree.popularmovies.ui.activities.MainActivity;
import com.udacity.nanodegree.popularmovies.ui.activities.adapters.FavoritesAdapter;
import com.udacity.nanodegree.popularmovies.ui.activities.adapters.MoviesAdapter;
import com.udacity.nanodegree.popularmovies.utils.LayoutUtils;
import com.udacity.nanodegree.popularmovies.utils.RetrofitUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter {

    private final MainActivity mainActivity;
    private final MoviesService moviesService;
    private final MovieDao movieDao;

    private int page = 1;
    private String query;
    private Disposable moviesSubscribe;

    public MainActivityPresenter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.moviesService = RetrofitUtils.getMoviesService();
        this.movieDao = AppDatabase.getInstance(mainActivity).movieDao();
    }

    public RecyclerView.LayoutManager getRecyclerLayoutManager() {
        return new GridLayoutManager(mainActivity, LayoutUtils.getSpanCount(mainActivity),
                LinearLayoutManager.VERTICAL, false);
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

    public void loadFavoritesAdapter(@Nullable List<MovieEntity> items) {
        mainActivity.loading = true;
        mainActivity.toggleLoading();

        if (items != null) {
            mainActivity.loading = false;
            mainActivity.toggleLoading();
            mainActivity.setMoviesRecyclerAdapter(new FavoritesAdapter());
            mainActivity.favoritesAdapter.setItems(items);
            return;
        }

        this.moviesSubscribe = movieDao.findAll().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(favorites -> {
                    mainActivity.loading = false;
                    mainActivity.toggleLoading();
                    mainActivity.setMoviesRecyclerAdapter(new FavoritesAdapter(favorites));
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

    public void disposeFavoritesSubscribe() {
        if (moviesSubscribe != null)
            moviesSubscribe.dispose();
    }
}
