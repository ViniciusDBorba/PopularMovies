package com.udacity.nanodegree.popularmovies.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.udacity.nanodegree.popularmovies.R;
import com.udacity.nanodegree.popularmovies.ui.activities.adapters.MoviesAdapter;
import com.udacity.nanodegree.popularmovies.ui.activities.presenters.MainActivityPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.movies_recycler)
    RecyclerView moviesRecycler;
    @BindView(R.id.right_option)
    TextView mostRatedOption;
    @BindView(R.id.left_option)
    TextView popularOption;

    private MainActivityPresenter presenter;
    public MoviesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainActivityPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (adapter != null)
            return;

        moviesRecycler.setLayoutManager(presenter.getRecyclerLayoutManager());
        presenter.getMoviesAdapter();


    }

    public void setMoviesRecyclerAdapter(MoviesAdapter moviesAdapter) {
        this.adapter = moviesAdapter;
        moviesRecycler.setAdapter(moviesAdapter);
    }

    @OnClick(R.id.right_option)
    public void onClickMostRated() {
        scrollToTop();

        if (mostRatedOption.getCurrentTextColor() == Color.WHITE)
            return;

        mostRatedOption.setTextColor(Color.WHITE);
        popularOption.setTextColor(getResources().getColor(R.color.font_disabled));
        presenter.filterMovies(getResources().getString(R.string.query_top_rated));
    }

    @OnClick(R.id.left_option)
    public void onClickPopular() {
        scrollToTop();

        if (popularOption.getCurrentTextColor() == Color.WHITE)
            return;


        mostRatedOption.setTextColor(getResources().getColor(R.color.font_disabled));
        popularOption.setTextColor(Color.WHITE);
        presenter.filterMovies(getResources().getString(R.string.query_popular));
    }

    private void scrollToTop() {
        moviesRecycler.getLayoutManager().smoothScrollToPosition(moviesRecycler, null, 0);
    }
}
