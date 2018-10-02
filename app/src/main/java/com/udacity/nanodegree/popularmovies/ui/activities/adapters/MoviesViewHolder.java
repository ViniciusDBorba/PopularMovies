package com.udacity.nanodegree.popularmovies.ui.activities.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.udacity.nanodegree.popularmovies.BuildConfig;
import com.udacity.nanodegree.popularmovies.R;
import com.udacity.nanodegree.popularmovies.data.MovieDTO;
import com.udacity.nanodegree.popularmovies.database.entities.MovieEntity;
import com.udacity.nanodegree.popularmovies.ui.activities.MainActivity;
import com.udacity.nanodegree.popularmovies.ui.activities.MovieDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoviesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.movie_image)
    ImageView moviePoster;

    private MovieEntity movie;


    MoviesViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, this.itemView);
    }

    public void bind(MovieDTO item) {
        this.movie = new MovieEntity(item);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(itemView.getContext())
                .load(BuildConfig.IMAGE_BASE_URL + item.getPosterPath())
                .apply(requestOptions)
                .into(moviePoster);
    }

    public void bind(MovieEntity item) {
        this.movie = item;

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(itemView.getContext())
                .load(BuildConfig.IMAGE_BASE_URL + item.getPosterPath())
                .apply(requestOptions)
                .into(moviePoster);
    }

    @OnClick(R.id.holder_root_layout)
    public void onClickItem(){
        Intent intent = new Intent(itemView.getContext(), MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.MOVIE_EXTRA, movie);

        itemView.getContext().startActivity(intent);
    }


}
