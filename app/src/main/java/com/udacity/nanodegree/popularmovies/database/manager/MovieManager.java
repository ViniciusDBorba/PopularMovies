package com.udacity.nanodegree.popularmovies.database.manager;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.udacity.nanodegree.popularmovies.database.AppDatabase;
import com.udacity.nanodegree.popularmovies.database.DAOs.MovieDao;
import com.udacity.nanodegree.popularmovies.database.entities.MovieEntity;

import java.util.List;

public class MovieManager {

    private MovieDao movieDao;
    private LiveData<List<MovieEntity>> movies;

    public MovieManager(Application application) {
        movieDao = AppDatabase.getInstance(application).movieDao();
        movies = movieDao.findAll();
    }

    public LiveData<List<MovieEntity>> getAllFavorites() {
        return movies;
    }

    public void insertMovie(MovieEntity movie) {
        movieDao.insert(movie);
    }

    public void removeMovie(MovieEntity movie) {
        movieDao.remove(movie);
    }

}
