package com.udacity.nanodegree.popularmovies.ui.activities.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.nanodegree.popularmovies.R;
import com.udacity.nanodegree.popularmovies.database.entities.MovieEntity;

import java.util.ArrayList;
import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<MoviesViewHolder> {
    private List<MovieEntity> movies = new ArrayList<>();

    public FavoritesAdapter(List<MovieEntity> movies) {
        this.movies.addAll(movies);
    }

    public FavoritesAdapter() {
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_list_item, viewGroup, false);
        return new MoviesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder moviesViewHolder, int i) {
        moviesViewHolder.bind(getItem(i));
    }

    @Override
    public int getItemCount() {
        if(movies == null)
            return 0;
        return movies.size();
    }

    public MovieEntity getItem(int position) {
        return movies.get(position);
    }


    public void addMovies(List<MovieEntity> movieEntity) {
        this.movies.addAll(movieEntity);
        this.notifyDataSetChanged();
    }

    public ArrayList<MovieEntity> getItems() {
        return new ArrayList<>(movies);
    }

    public void setItems(List<MovieEntity> items) {
        this.movies = items;
        this.notifyDataSetChanged();
    }
}
