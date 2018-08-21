package com.udacity.nanodegree.popularmovies.ui.activities.adapters;

import android.support.annotation.NonNull;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.movie_image)
    ImageView moviePoster;


    MoviesViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, this.itemView);
    }

    public void bind(MovieDTO item) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(itemView.getContext())
                .load(BuildConfig.IMAGE_BASE_URL + item.getPosterPath())
                .apply(requestOptions)
                .into(moviePoster);
    }
}
