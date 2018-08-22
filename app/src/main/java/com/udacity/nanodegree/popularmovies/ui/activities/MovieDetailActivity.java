package com.udacity.nanodegree.popularmovies.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.udacity.nanodegree.popularmovies.BuildConfig;
import com.udacity.nanodegree.popularmovies.R;
import com.udacity.nanodegree.popularmovies.data.MovieDTO;
import com.udacity.nanodegree.popularmovies.ui.activities.presenters.MovieDetailPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {
    public static String MOVIE_EXTRA = "MOVIE";

    @BindView(R.id.movie_poster)
    ImageView moviePoster;

    @BindView(R.id.movie_title)
    TextView movieTitle;

    @BindView(R.id.movie_overview)
    TextView movieOverview;

    @BindView(R.id.movie_vote_average)
    TextView voteAverage;

    private MovieDetailPresenter presenter;
    private MovieDTO movie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        this.presenter = new MovieDetailPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (movie == null) {
            movie = (MovieDTO) getIntent().getSerializableExtra(MOVIE_EXTRA);
            refreshView();
        }
    }

    private void refreshView() {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions
                .centerInside()
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(this)
                .load(BuildConfig.IMAGE_BASE_URL + movie.getPosterPath())
                .apply(requestOptions)
                .into(moviePoster);

        movieTitle.setText(movie.getOriginalTitle());
        movieOverview.setText(movie.getOverview());
        voteAverage.setText(String.valueOf(movie.getVoteAverage()));
    }
}