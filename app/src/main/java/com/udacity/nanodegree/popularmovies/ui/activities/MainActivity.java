package com.udacity.nanodegree.popularmovies.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.util.Consumer;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.nanodegree.popularmovies.R;
import com.udacity.nanodegree.popularmovies.data.MovieDTO;
import com.udacity.nanodegree.popularmovies.database.entities.MovieEntity;
import com.udacity.nanodegree.popularmovies.ui.activities.adapters.FavoritesAdapter;
import com.udacity.nanodegree.popularmovies.ui.activities.adapters.MoviesAdapter;
import com.udacity.nanodegree.popularmovies.ui.activities.presenters.MainActivityPresenter;
import com.udacity.nanodegree.popularmovies.utils.InternetCheckerAsyncTask;

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
    @BindView(R.id.center_option)
    TextView favoriteOption;
    @BindView(R.id.left_option)
    TextView popularOption;
    @BindView(R.id.movies_progres)
    ContentLoadingProgressBar moviesProgress;

    private MainActivityPresenter presenter;
    public MoviesAdapter moviesAdapter;
    public FavoritesAdapter favoritesAdapter;


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

        new InternetCheckerAsyncTask(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean internet) {
                if (!internet){
                    Toast.makeText(MainActivity.this, MainActivity.this.getResources().getString(R.string.no_internet), Toast.LENGTH_LONG).show();
                }
            }
        }).execute();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (moviesAdapter == null && favoritesAdapter == null) {
            return;
        }

        outState.putInt(PAGE_STATE, presenter.getPage());
        outState.putString(QUERY_STATE, presenter.getQuery());
        outState.putParcelableArrayList(ITEMS_STATE, moviesAdapter == null ? favoritesAdapter.getItems() : moviesAdapter.getItems());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (moviesAdapter != null || favoritesAdapter != null)
            return;

        moviesRecycler.setLayoutManager(presenter.getRecyclerLayoutManager());
        presenter.loadMoviesAdapter(null);

        refreshRecyclerScrollListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.disposeFavoritesSubscribe();
    }

    public void refresh(Bundle state) {
        moviesRecycler.setLayoutManager(presenter.getRecyclerLayoutManager());
        String query = state.getString(QUERY_STATE);

        if (query == null) {
            return;
        }

        presenter.setQuery(query);

        if (query.equals(getResources().getString(R.string.query_popular))) {
            popularOption.setTextColor(Color.WHITE);
            mostRatedOption.setTextColor(getResources().getColor(R.color.font_disabled));
            favoriteOption.setTextColor(getResources().getColor(R.color.font_disabled));

        } else if (query.equals(getResources().getString(R.string.query_top_rated))) {
            mostRatedOption.setTextColor(Color.WHITE);
            popularOption.setTextColor(getResources().getColor(R.color.font_disabled));
            favoriteOption.setTextColor(getResources().getColor(R.color.font_disabled));

        } else {
            favoriteOption.setTextColor(Color.WHITE);
            popularOption.setTextColor(getResources().getColor(R.color.font_disabled));
            mostRatedOption.setTextColor(getResources().getColor(R.color.font_disabled));
        }

        presenter.setPage(state.getInt(PAGE_STATE));

        if (query.equals(getResources().getString(R.string.query_favorites))) {
            loadFavorites(state);
        } else {
            loadMovies(state);
        }
    }

    private void loadMovies(Bundle state) {
        List<MovieDTO> items = state.getParcelableArrayList(ITEMS_STATE);

        presenter.loadMoviesAdapter(items);

        refreshRecyclerScrollListener();
    }

    private void loadFavorites(Bundle state) {
        List<MovieEntity> items = state.getParcelableArrayList(ITEMS_STATE);

        presenter.loadFavoritesAdapter(items);
    }

    public void setMoviesRecyclerAdapter(MoviesAdapter moviesAdapter) {
        this.moviesAdapter = moviesAdapter;
        moviesRecycler.setAdapter(moviesAdapter);
    }

    public void setMoviesRecyclerAdapter(FavoritesAdapter favoritesAdapter) {
        this.favoritesAdapter = favoritesAdapter;
        moviesRecycler.swapAdapter(favoritesAdapter, true);
    }

    @OnClick(R.id.right_option)
    public void onClickMostRated() {
        scrollToTop();

        if (mostRatedOption.getCurrentTextColor() == Color.WHITE)
            return;

        if (isFavoriteSelected()) {
            refreshRecyclerScrollListener();
            presenter.loadMoviesAdapter(getResources().getString(R.string.query_top_rated), 1);

            popularOption.setTextColor(getResources().getColor(R.color.font_disabled));
            favoriteOption.setTextColor(getResources().getColor(R.color.font_disabled));
            mostRatedOption.setTextColor(Color.WHITE);

            return;
        }

        popularOption.setTextColor(getResources().getColor(R.color.font_disabled));
        favoriteOption.setTextColor(getResources().getColor(R.color.font_disabled));
        mostRatedOption.setTextColor(Color.WHITE);

        presenter.filterMovies(getResources().getString(R.string.query_top_rated), 1);
    }

    @OnClick(R.id.left_option)
    public void onClickPopular() {
        scrollToTop();

        if (popularOption.getCurrentTextColor() == Color.WHITE)
            return;

        if (isFavoriteSelected()) {
            refreshRecyclerScrollListener();
            presenter.loadMoviesAdapter(getResources().getString(R.string.query_popular), 1);

            mostRatedOption.setTextColor(getResources().getColor(R.color.font_disabled));
            favoriteOption.setTextColor(getResources().getColor(R.color.font_disabled));
            popularOption.setTextColor(Color.WHITE);
            return;
        }

        mostRatedOption.setTextColor(getResources().getColor(R.color.font_disabled));
        favoriteOption.setTextColor(getResources().getColor(R.color.font_disabled));
        popularOption.setTextColor(Color.WHITE);

        presenter.filterMovies(getResources().getString(R.string.query_popular), 1);
    }

    @OnClick(R.id.center_option)
    public void onClickFavorite() {
        scrollToTop();

        if (favoriteOption.getCurrentTextColor() == Color.WHITE)
            return;

        mostRatedOption.setTextColor(getResources().getColor(R.color.font_disabled));
        popularOption.setTextColor(getResources().getColor(R.color.font_disabled));
        favoriteOption.setTextColor(Color.WHITE);

        presenter.loadFavoritesAdapter(null);
    }

    private void refreshRecyclerScrollListener() {
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

    private boolean isFavoriteSelected() {
        return favoriteOption.getCurrentTextColor() == Color.WHITE;
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
