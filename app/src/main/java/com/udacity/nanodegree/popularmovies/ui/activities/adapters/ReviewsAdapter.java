package com.udacity.nanodegree.popularmovies.ui.activities.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.nanodegree.popularmovies.R;
import com.udacity.nanodegree.popularmovies.data.ReviewDTO;
import com.udacity.nanodegree.popularmovies.data.ReviewResultDTO;

import java.util.ArrayList;
import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsViewHolder> {

    List<ReviewDTO> reviews = new ArrayList<>();

    public ReviewsAdapter(ReviewResultDTO reviewResultDTO) {
        this.reviews = reviewResultDTO.getResults();
        notifyDataSetChanged();
    }

    public ReviewsAdapter() {
    }

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_list_item, viewGroup, false);
        return new ReviewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsViewHolder reviewsViewHolder, int i) {
        reviewsViewHolder.bind(getItem(i));
    }

    public ReviewDTO getItem(int position) {
        return reviews.get(position);
    }

    @Override
    public int getItemCount() {
        if (reviews == null)
            return 0;
        return reviews.size();
    }

    public void setItems(List<ReviewDTO> items) {
        reviews = items;
    }

    public void addItems(List<ReviewDTO> results) {
        reviews.addAll(results);
        notifyDataSetChanged();
    }
}
