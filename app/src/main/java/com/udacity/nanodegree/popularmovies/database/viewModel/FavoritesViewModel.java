package com.udacity.nanodegree.popularmovies.database.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.udacity.nanodegree.popularmovies.database.entities.MovieEntity;
import com.udacity.nanodegree.popularmovies.database.manager.MovieManager;
import com.udacity.nanodegree.popularmovies.ui.activities.MainActivity;

import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {

    private MovieManager manager;

    private LiveData<List<MovieEntity>> favorites;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        manager = new MovieManager(application);
        favorites = manager.getAllFavorites();
    }

    public LiveData<List<MovieEntity>> getFavoritesLiveData() {
        return favorites;
    }

    public void insertOrRemoveFavorite(MovieEntity movie) {
        if (favorites.getValue() != null && favorites.getValue().contains(movie)) {
            manager.removeMovie(movie);
        } else {
            manager.insertMovie(movie);
        }
    }

    public void removeObservable(MainActivity mainActivity) {
        favorites.removeObservers(mainActivity);
    }

    public List<MovieEntity> getAllFavorites() {
        return favorites.getValue();
    }
}
