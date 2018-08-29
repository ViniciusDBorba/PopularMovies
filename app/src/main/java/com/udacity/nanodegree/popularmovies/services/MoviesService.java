package com.udacity.nanodegree.popularmovies.services;

import com.udacity.nanodegree.popularmovies.BuildConfig;
import com.udacity.nanodegree.popularmovies.data.ResultDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesService {

    @GET("movie/{query}" + BuildConfig.MOVIEDB_KEY)
    Call<ResultDTO> getMovies(@Path("query") String query, @Query("page") int page);
}
