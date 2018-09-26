package com.udacity.nanodegree.popularmovies.ui.activities.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.udacity.nanodegree.popularmovies.R;
import com.udacity.nanodegree.popularmovies.data.ReviewDTO;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.author_name)
    TextView authorName;

    @BindView(R.id.review_text)
    TextView reviewText;

    public ReviewsViewHolder(@NonNull View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void bind(ReviewDTO reviewDTO) {
        authorName.setText(reviewDTO.getAuthor());
        reviewText.setText(reviewDTO.getContent());
    }
}
