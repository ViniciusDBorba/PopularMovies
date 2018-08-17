package com.udacity.nanodegree.popularmovies.ui.activities.adapters;

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

import retrofit2.Call;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesViewHolder> {

    private List<MovieDTO> movies = new ArrayList<>();

    public MoviesAdapter(ResultDTO result) {
        movies.addAll(result.getResults());
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
}
