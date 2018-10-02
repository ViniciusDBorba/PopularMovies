package com.udacity.nanodegree.popularmovies.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.udacity.nanodegree.popularmovies.BuildConfig;
import com.udacity.nanodegree.popularmovies.R;
import com.udacity.nanodegree.popularmovies.data.MovieDTO;
import com.udacity.nanodegree.popularmovies.database.entities.MovieEntity;
import com.udacity.nanodegree.popularmovies.ui.activities.adapters.ReviewsAdapter;
import com.udacity.nanodegree.popularmovies.ui.activities.adapters.TrailerAdapter;
import com.udacity.nanodegree.popularmovies.ui.activities.presenters.MovieDetailPresenter;
import com.udacity.nanodegree.popularmovies.ui.components.SaveInstanceRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.movie_release_date)
    TextView releaseDate;
    @BindView(R.id.trailers_recycler)
    SaveInstanceRecyclerView trailersRecycler;
    @BindView(R.id.reviews_recycler)
    SaveInstanceRecyclerView reviewsRcycler;
    @BindView(R.id.list_progres)
    ContentLoadingProgressBar listLoading;
    @BindView(R.id.favorite_button)
    FloatingActionButton favoriteButton;

    public boolean loading = false;

    private MovieDetailPresenter presenter;

    private MovieEntity movie;

    private int listThreshold = 3;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        setupActionBar();

    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar == null) {
            return;
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (movie == null) {
            movie = getIntent().getParcelableExtra(MOVIE_EXTRA);
            refreshView();
        }

    }

    private void refreshView() {
        if (presenter == null) {
            presenter = new MovieDetailPresenter(this, movie);
        }

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
        releaseDate.setText(getResources().getString(R.string.release_date, movie.getReleaseDate()));

        if (presenter.isFavorite()) {
            fillStar();
        } else {
            unfillStar();
        }

        trailersRecycler.setLayoutManager(presenter.getRecyclerLayoutManager());
        presenter.loadTrailersAdapter(null);

        reviewsRcycler.setLayoutManager(presenter.getRecyclerLayoutManager());
        presenter.loadReviewsAdapter(null);

        reviewsRcycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int itemsCount = layoutManager.getItemCount();
                int lastPosition = layoutManager.findLastVisibleItemPosition();

                if (itemsCount <= (lastPosition + listThreshold)) {
                    presenter.nextReviewsPage();
                }
            }
        });



    }

    public void toggleLoading() {
        if (loading) {
            listLoading.show();
            listLoading.setVisibility(View.VISIBLE);
        } else {
            listLoading.hide();
            listLoading.setVisibility(View.GONE);
        }

    }

    public void setTrailersRecyclerAdapter(TrailerAdapter trailerAdapter) {
        trailersRecycler.setAdapter(trailerAdapter);
    }

    public void setReviewsRecyclerAdapter(ReviewsAdapter reviewAdapter) {
        reviewsRcycler.setAdapter(reviewAdapter);
    }

    @OnClick(R.id.favorite_button)
    public void onClickFavorite() {
        presenter.favoriteMovie();
    }

    public void unfillStar() {
        favoriteButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_star));
    }

    public void fillStar() {
        favoriteButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_sta_fill));
    }
}
