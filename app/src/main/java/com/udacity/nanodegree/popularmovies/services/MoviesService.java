package com.udacity.nanodegree.popularmovies.services;

import com.udacity.nanodegree.popularmovies.BuildConfig;
import com.udacity.nanodegree.popularmovies.data.MoviesResultDTO;
import com.udacity.nanodegree.popularmovies.data.ReviewResultDTO;
import com.udacity.nanodegree.popularmovies.data.VideosResultDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesService {

    @GET("movie/{query}?api_key=" + BuildConfig.MOVIEDB_KEY)
    Call<MoviesResultDTO> getMovies(@Path("query") String query, @Query("page") int page);

    @GET("movie/{movieId}/videos?api_key=" + BuildConfig.MOVIEDB_KEY)
    Call<VideosResultDTO> getVideos(@Path("movieId") String movieId);

    @GET("movie/{movieId}/reviews?api_key=" + BuildConfig.MOVIEDB_KEY)
    Call<ReviewResultDTO> getReviews(@Path("movieId") String movieId, @Query("page") int page);
}
