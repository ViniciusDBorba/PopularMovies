package com.udacity.nanodegree.popularmovies.database.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.udacity.nanodegree.popularmovies.database.entities.MovieEntity;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieEntity movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Iterable<MovieEntity> movies);

    @Query("SELECT * FROM movie")
    Flowable<MovieEntity> findAll();

}
