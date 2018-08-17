package com.udacity.nanodegree.popularmovies.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.udacity.nanodegree.popularmovies.R;
import com.udacity.nanodegree.popularmovies.ui.activities.adapters.MoviesAdapter;
import com.udacity.nanodegree.popularmovies.ui.activities.presenters.MainActivityPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.movies_recycler)
    RecyclerView moviesRecycler;

    private MainActivityPresenter presenter;
    private MoviesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainActivityPresenter(this);

        adapter = presenter.getMoviesAdapter();

    }

    @Override
    protected void onResume() {
        super.onResume();

        moviesRecycler.setLayoutManager(presenter.getRecyclerLayoutManager());
        moviesRecycler.setAdapter(adapter);
    }
}
