package com.udacity.nanodegree.popularmovies.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.udacity.nanodegree.popularmovies.R;
import com.udacity.nanodegree.popularmovies.data.MovieDTO;
import com.udacity.nanodegree.popularmovies.ui.activities.adapters.MoviesAdapter;
import com.udacity.nanodegree.popularmovies.ui.activities.presenters.MainActivityPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String QUERY_STATE = "QUERY_STATE";
    private static final String PAGE_STATE = "PAGE_STATE";
    private static final String ITEMS_STATE = "ITEMS_STATE";


    @BindView(R.id.movies_recycler)
    RecyclerView moviesRecycler;
    @BindView(R.id.right_option)
    TextView mostRatedOption;
    @BindView(R.id.left_option)
    TextView popularOption;
    @BindView(R.id.movies_progres)
    ContentLoadingProgressBar moviesProgress;

    private MainActivityPresenter presenter;
    public MoviesAdapter adapter;


    public boolean loading = false;
    private int listThreshold = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainActivityPresenter(this);

        if (savedInstanceState != null) {
            refresh(savedInstanceState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(PAGE_STATE, presenter.getPage());
        outState.putString(QUERY_STATE, presenter.getQuery());
        outState.putParcelableArrayList(ITEMS_STATE, adapter.getItems());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (adapter != null)
            return;

        moviesRecycler.setLayoutManager(presenter.getRecyclerLayoutManager());
        presenter.loadMoviesAdapter(null);

        moviesRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                int itemsCount = layoutManager.getItemCount();
                int lastPosition = layoutManager.findLastVisibleItemPosition();

                if (itemsCount <= (lastPosition + listThreshold)) {
                    presenter.nextPage();
                }
            }
        });
    }

    public void refresh(Bundle state) {
        moviesRecycler.setLayoutManager(presenter.getRecyclerLayoutManager());
        String query = state.getString(QUERY_STATE);
        presenter.setQuery(query);

        if (query.equals(getResources().getString(R.string.query_popular))) {
            mostRatedOption.setTextColor(getResources().getColor(R.color.font_disabled));
            popularOption.setTextColor(Color.WHITE);
        } else {
            mostRatedOption.setTextColor(Color.WHITE);
            popularOption.setTextColor(getResources().getColor(R.color.font_disabled));
        }

        presenter.setPage(state.getInt(PAGE_STATE));
        List<MovieDTO> items = state.getParcelableArrayList(ITEMS_STATE);

        presenter.loadMoviesAdapter(items);

        moviesRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                int itemsCount = layoutManager.getItemCount();
                int lastPosition = layoutManager.findLastVisibleItemPosition();

                if (itemsCount <= (lastPosition + listThreshold)) {
                    presenter.nextPage();
                }
            }
        });
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
        presenter.filterMovies(getResources().getString(R.string.query_top_rated), 1);
    }

    @OnClick(R.id.left_option)
    public void onClickPopular() {
        scrollToTop();

        if (popularOption.getCurrentTextColor() == Color.WHITE)
            return;


        mostRatedOption.setTextColor(getResources().getColor(R.color.font_disabled));
        popularOption.setTextColor(Color.WHITE);
        presenter.filterMovies(getResources().getString(R.string.query_popular), 1);
    }

    private void scrollToTop() {
        moviesRecycler.getLayoutManager().smoothScrollToPosition(moviesRecycler, null, 0);
    }

    public void toggleLoading() {
        if (loading) {
            moviesProgress.show();
            moviesProgress.setVisibility(View.VISIBLE);
        } else {
            moviesProgress.hide();
            moviesProgress.setVisibility(View.GONE);
        }

    }
}
