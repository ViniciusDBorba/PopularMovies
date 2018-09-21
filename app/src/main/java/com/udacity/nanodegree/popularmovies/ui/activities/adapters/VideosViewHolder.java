package com.udacity.nanodegree.popularmovies.ui.activities.adapters;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.udacity.nanodegree.popularmovies.BuildConfig;
import com.udacity.nanodegree.popularmovies.R;
import com.udacity.nanodegree.popularmovies.data.MovieDTO;
import com.udacity.nanodegree.popularmovies.data.VideoDTO;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideosViewHolder extends RecyclerView.ViewHolder {

    private static final String TRAILER_MESSAGE = "See this movie trailer: \n." + BuildConfig.YOUTUBE_BASE_URL;
    private static final String TYPE = "text/plain";

    @BindView(R.id.trailer_thumbnail)
    ImageView thumbImg;

    private VideoDTO item;

    public VideosViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, this.itemView);
    }

    public void bind(VideoDTO item) {
        this.item = item;
    }

    public void onClickItem(){
        AlertDialog builder = new AlertDialog.Builder(itemView.getContext())
                .setTitle(itemView.getResources().getString(R.string.choose))
                .setItems(R.array.trailer_options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                itemView.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(BuildConfig.YOUTUBE_BASE_URL, item.getKey()))));
                                break;
                            case 1:
                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_TEXT, String.format(TRAILER_MESSAGE, item.getKey()));
                                sendIntent.setType(TYPE);
                                itemView.getContext().startActivity(sendIntent);
                                break;
                        }
                    }
                }).create();
        builder.show();
    }

}
