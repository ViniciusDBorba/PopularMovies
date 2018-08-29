package com.udacity.nanodegree.popularmovies.ui.activities.adapters;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.nanodegree.popularmovies.R;
import com.udacity.nanodegree.popularmovies.data.MovieDTO;
import com.udacity.nanodegree.popularmovies.data.ResultDTO;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesViewHolder> {

    private List<MovieDTO> movies = new ArrayList<>();

    public MoviesAdapter(ResultDTO result) {
        movies.addAll(result.getResults());
    }

    public MoviesAdapter() {
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
        return movies.size();
    }

    public MovieDTO getItem(int position) {
        return movies.get(position);
    }

    public void filterMovies(ResultDTO resultDTO) {
        this.movies = resultDTO.getResults();
        this.notifyDataSetChanged();
    }

    public void addMovies(ResultDTO resultDTO) {
        this.movies.addAll(resultDTO.getResults());
        this.notifyDataSetChanged();
    }

    public ArrayList<MovieDTO> getItems() {
        return new ArrayList<>(movies);
    }

    public void setItems(List<MovieDTO> items) {
        this.movies = items;
        this.notifyDataSetChanged();
    }
}
