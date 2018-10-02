package com.udacity.nanodegree.popularmovies.database.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.udacity.nanodegree.popularmovies.database.entities.MovieEntity;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface MovieDao {

    @Insert()
    void insert(MovieEntity movie);

    @Query("SELECT * FROM movie")
    Flowable<List<MovieEntity>> findAll();

    @Query("SELECT * FROM movie WHERE movie.id == :movie")
    MovieEntity getMovieById(int movie);

    @Delete()
    void removeMovie(MovieEntity movie);
}
